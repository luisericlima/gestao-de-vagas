package br.com.devluis.gestaodevagas.modules.candidate.useCases;

import br.com.devluis.gestaodevagas.exceptions.JobNotFoundException;
import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.ApplyJobEntity;
import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.CandidateEntity;
import br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories.ApplyJobRepository;
import br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories.CandidateRepository;
import br.com.devluis.gestaodevagas.modules.company.entities.JobEntity;
import br.com.devluis.gestaodevagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks //cria uma instancia real da classe, porem as outras classes instanciadas dentro dela precisam vir mockadas
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;


    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    //validar se o candidato não existe
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            applyJobCandidateUseCase.execute(null, null);

        }catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }


    @Test
    //validar se a vaga existe
    public void should_not_be_able_to_apply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate)); // quando buscar o candidato por Id, ele tem que retorna um candidate optional(Pode ser NULL ou não)
        try{
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class); //afirmar que a variavel E é instancia da classe JobNotFoundException
        }
    }

    @Test

    // TUDO QUE AS CLASSES DE TESTES PEDEM COMO PARAMETRO DEVEM SER SETADAS MANUALMENTE QUANDO FOREM TESTAR
    public void should_be_able_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID(); // gerando idCandidate para teste

        var idJob = UUID.randomUUID(); // gerando idJob para teste

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity())); // Ao buscar por IdCandidate ele tem que retorna a instância do candidate ou não(POR ISSO O OPTIONAL)
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity())); // aqui tambem deve retorna a instancia da vaga
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated); // quando o applyrepository for chamado para salvar o applyJob ele devera retorna o id do applyJob

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id"); //verifica se a variavel result tem alguma propriedade de nome ID
        assertNotNull(result.getId()); // garantir que o Id do ApplyJobEntity não é null
    }
}
