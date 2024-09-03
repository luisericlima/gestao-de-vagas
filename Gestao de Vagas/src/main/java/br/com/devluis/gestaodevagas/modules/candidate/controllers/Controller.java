package br.com.devluis.gestaodevagas.modules.candidate.controllers;

import br.com.devluis.gestaodevagas.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class Controller {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;




    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate){
        try {
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
