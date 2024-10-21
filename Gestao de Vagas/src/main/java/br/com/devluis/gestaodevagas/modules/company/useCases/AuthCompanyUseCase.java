package br.com.devluis.gestaodevagas.modules.company.useCases;

import javax.naming.AuthenticationException;
import br.com.devluis.gestaodevagas.modules.company.dto.AuthCompanyDTO;
import br.com.devluis.gestaodevagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}") //recebendo o token que esta no properties e colocando aqui
    private String secretKey;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyRepository companyRepository;


    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                ()-> {throw new UsernameNotFoundException("User/password incorrect");}
        );
        //Verificacao se senha é igual
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        //Se as senhas nao coincidirem ira ser gerado um excecao
        if (!passwordMatches) {
            throw new AuthenticationException();
        }
        //Se for igual -> gerar tokens
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .sign(algorithm);
        return token;
        // essa linha de comando retorna uma String por isso foi declarado o retorno do metodo
    }
}