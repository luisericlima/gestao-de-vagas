package br.com.devluis.gestaodevagas.modules.candidate.useCases;

import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.CandidateEntity;
import br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories.CandidateRepository;
import br.com.devluis.gestaodevagas.exceptions.UserFoundException;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    Algorithm algorithm = Algorithm.HMAC256("secret");
    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),
                candidateEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });
        var password = passwordEncoder.encode(candidateEntity.getPassword()); //encripta a senha
        candidateEntity.setPassword(password); //setta a senha encriptada no atributo password
        return this.candidateRepository.save(candidateEntity);
    }


}
