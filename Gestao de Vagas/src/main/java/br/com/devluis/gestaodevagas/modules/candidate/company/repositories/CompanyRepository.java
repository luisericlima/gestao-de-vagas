package br.com.devluis.gestaodevagas.modules.candidate.company.repositories;

import br.com.devluis.gestaodevagas.modules.candidate.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID>{
    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);

}
