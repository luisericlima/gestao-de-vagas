package br.com.devluis.gestaodevagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "job")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;

    @NotBlank(message = "Este campo é obrigatório")
    private String level;
    private String benefits;

    @ManyToOne()
    //Abaixo na variavel NAME é declarado o atributo que sera a chave estrangeira que no caso é o companyId
    //Insertable e updatable impede da tabela CompanyEntity ser modificada, dessa forma ela só está ali para passar o ID dela.
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
