package br.com.devluis.gestaodevagas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class Controller {

    @PostMapping("/")
    public ResponseEntity<CandidateEntity> create(@RequestBody CandidateEntity candidate){
        System.out.println(candidate.getEmail());
        return ResponseEntity.ok().body(candidate);
    }
}
