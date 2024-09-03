package br.com.devluis.gestaodevagas.modules.candidate.useCases;

import br.com.devluis.gestaodevagas.modules.candidate.controllers.CandidateEntity;
import br.com.devluis.gestaodevagas.modules.candidate.controllers.CandidateRepository;
import br.com.devluis.gestaodevagas.modules.candidate.exceptions.UserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),
                candidateEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });
        return this.candidateRepository.save(candidateEntity);
    }


}
