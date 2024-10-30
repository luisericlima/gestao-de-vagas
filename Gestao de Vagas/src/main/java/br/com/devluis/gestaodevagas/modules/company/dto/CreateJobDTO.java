package br.com.devluis.gestaodevagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO{
    @Schema(example = "Vaga para pessoa desenvolvedora senior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "GYMPass, Plano de saúde, VA e VR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "SENIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
