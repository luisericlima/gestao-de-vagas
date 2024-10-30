package br.com.devluis.gestaodevagas.modules.company.controllers;

import br.com.devluis.gestaodevagas.modules.company.entities.CompanyEntity;
import br.com.devluis.gestaodevagas.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
//@Tag(name = "Company", description = "Informações da empresa")
public class CompanyController {
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
//    @PreAuthorize("hasRole('COMPANY')")
//    @Operation(summary = "Cadastro de empresas", description = "Essa função é responsável por cadastrar uma empresa")

    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        try {
            var result = createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
