package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDTO {
    @NotBlank
    @Size(max = 100, message = "O campo deve possuir no máximo 100 caracteres.")
    private String titulo;

    @NotNull
    @FutureOrPresent
    private LocalDate dataEvento;

    private Integer quantidadeParticipantes;

    @NotNull
    private String codigo;
}
