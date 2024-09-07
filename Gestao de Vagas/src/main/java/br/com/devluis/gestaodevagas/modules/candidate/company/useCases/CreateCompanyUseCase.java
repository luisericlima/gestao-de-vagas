package br.com.devluis.gestaodevagas.modules.candidate.company.useCases;

import br.com.devluis.gestaodevagas.modules.candidate.company.entities.CompanyEntity;
import br.com.devluis.gestaodevagas.modules.candidate.company.repositories.CompanyRepository;
import br.com.devluis.gestaodevagas.modules.candidate.exceptions.UserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });
        return this.companyRepository.save(companyEntity);

    }




}
