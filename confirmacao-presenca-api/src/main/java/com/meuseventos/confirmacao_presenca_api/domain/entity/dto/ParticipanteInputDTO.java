package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipanteInputDTO {
    @NotBlank
    @Size(max = 100, message = "O campo deve possuir no máximo 100 caracteres.")
    private String nome;

    @NotBlank
    @Size(max = 11, message = "O campo deve possuir no máximo 11 caracteres.")
    private String cpf;
}
