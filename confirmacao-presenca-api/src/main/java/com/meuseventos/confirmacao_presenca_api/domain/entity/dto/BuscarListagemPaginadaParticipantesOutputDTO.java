package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuscarListagemPaginadaParticipantesOutputDTO {
    UUID id;
    String nome;
    String cpf;
}
