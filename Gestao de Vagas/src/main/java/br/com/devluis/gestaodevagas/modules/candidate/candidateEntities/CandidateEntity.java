package br.com.devluis.gestaodevagas.modules.candidate.candidateEntities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
public class CandidateEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Maria da Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do candidato")
    private String name;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
    @Schema(example = "mariaSilva", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do candidato")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido")
    @Schema(example = "mariasilva@email.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email do candidato")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve ter entre 10 e 100 caracteres")
    @Schema(example = "exemplo123@", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;

    @Schema(example = "Developer Java", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve descrição do candidato")//requiredMode identifica que o campo é obrigatorio
    private String description;
    private String curriculum;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
