package br.com.devluis.gestaodevagas.modules.candidate.useCases;

import br.com.devluis.gestaodevagas.exceptions.JobNotFoundException;
import br.com.devluis.gestaodevagas.exceptions.UserNotFoundException;
import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.ApplyJobEntity;
import br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories.ApplyJobRepository;
import br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories.CandidateRepository;
import br.com.devluis.gestaodevagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob){

        // verifica se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(()->{
            throw new UserNotFoundException();
        });

        // verifica se job existe
        this.jobRepository.findById(idJob).orElseThrow(()->{
            throw new JobNotFoundException();
        });

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        return applyJobRepository.save(applyJob);
    }
}
