package br.com.devluis.gestaodevagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Desenvolvedor Java")
    private String description;

    @Schema(example = "Eric")
    private String username;
    @Schema(example = "chico223@email.com")
    private String email;

    private UUID id;

    @Schema(example = "Chico Jose")
    private String name;
}
