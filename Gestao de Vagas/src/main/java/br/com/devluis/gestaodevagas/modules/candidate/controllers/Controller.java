package br.com.devluis.gestaodevagas.modules.candidate.controllers;

import br.com.devluis.gestaodevagas.modules.candidate.CandidateEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class Controller {

    @PostMapping("/")
    public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidate){
        System.out.println("Candidato: " + candidate);
        System.out.println(candidate.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(candidate);

    }
}
