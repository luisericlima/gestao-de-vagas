package br.com.devluis.gestaodevagas.modules.company.useCases;

import javax.naming.AuthenticationException;
import br.com.devluis.gestaodevagas.modules.company.dto.AuthCompanyDTO;
import br.com.devluis.gestaodevagas.modules.company.dto.AuthCompanyResponseDTO;
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
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}") //recebendo o token que esta no properties e colocando aqui
    private String secretKey;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyRepository companyRepository;


    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
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

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);
        var authCompanyResponseDTO =AuthCompanyResponseDTO.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
        return authCompanyResponseDTO;
        // essa linha de comando retorna uma String por isso foi declarado o retorno do metodo
    }
}