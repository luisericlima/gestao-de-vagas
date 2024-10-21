package br.com.devluis.gestaodevagas.modules.company.controllers;

import br.com.devluis.gestaodevagas.modules.company.dto.CreateJobDTO;
import br.com.devluis.gestaodevagas.modules.company.entities.JobEntity;
import br.com.devluis.gestaodevagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){
        var companyId = request.getAttribute("company_Id"); //recupera atributo que está autorizado
        //variavel acima retorna um objeto, porem preciso do tipo UUID, para isso fazemos conversao
        //jobEntity.setCompanyId(UUID.fromString(companyId.toString()));
        var jobEntity = JobEntity.builder().companyId(UUID.fromString(companyId.toString())).benefits(createJobDTO.getBenefits()).description(createJobDTO.getBenefits()).level(createJobDTO.getLevel()).build();
        return this.createJobUseCase.execute(jobEntity);
    }
}