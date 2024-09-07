package br.com.devluis.gestaodevagas.modules.candidate.candidateRepositories;

import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email); //poderia ser AND ou OR na nomenclatura
    // Spring entende o finBy e procura conforme o que foi declarado
}
