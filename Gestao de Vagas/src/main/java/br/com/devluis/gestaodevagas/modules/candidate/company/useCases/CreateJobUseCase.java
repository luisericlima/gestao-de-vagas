package br.com.devluis.gestaodevagas.modules.candidate.company.useCases;

import br.com.devluis.gestaodevagas.modules.candidate.company.entities.JobEntity;
import br.com.devluis.gestaodevagas.modules.candidate.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity){
        return this.jobRepository.save(jobEntity);
    }
}
