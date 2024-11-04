package br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories;

import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository  extends JpaRepository<ApplyJobEntity, UUID> {
}
