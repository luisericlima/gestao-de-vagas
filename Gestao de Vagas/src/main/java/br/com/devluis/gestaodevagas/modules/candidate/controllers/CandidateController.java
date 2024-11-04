package br.com.devluis.gestaodevagas.modules.candidate.controllers;

import br.com.devluis.gestaodevagas.modules.candidate.candidateEntities.CandidateEntity;
import br.com.devluis.gestaodevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.devluis.gestaodevagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.devluis.gestaodevagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.devluis.gestaodevagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.devluis.gestaodevagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.devluis.gestaodevagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")

public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;



    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })

    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate){
        try {
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')") //determina que so quem tem a role candidate pode acessar essa rota, ROLES SEMPRE VEM EM UPPERCASE POR ISSO ESTA EM UPPERCASE AQUI TAMBEM
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {//COMO ESTAMOS LIDANDO COM USUARIOS JÁ AUTENTICADOS DEVEMOS SEMPRE RECUPERAR OS DADOS QUE QUEREMOS NA REQUEST
        var idCandidate = request.getAttribute("candidate_id"); // recuperando o candidate_id da request
        try {
            var profile = this.profileCandidateUseCase
                    .execute(UUID.fromString(idCandidate.toString())); // quando o idCandidate vem da requisicao ele vem como String e precisamos de um UUID por isso é feito a conversao
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponivel para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseadas no filtro") //adiciona informacoes sobre a rota
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = { //o ApiReponse define o status code a ser retornado, nesse caso é 200
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)) //exibe um exemplo de dados que devera ser exibido nessa rota
                    )
            })
    )

    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga",
                description = "Essa função é responspavel por realizar a inscrição do candidato em uma vaga."
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyjob(HttpServletRequest request, @RequestBody UUID job) {
        try {
            var idCandidate = request.getAttribute("candidate_id");
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), job);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}