package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuscarEventoPorCodigoOutputDTO {
    private String titulo;

    private String codigo;

    Page<BuscarListagemPaginadaParticipantesOutputDTO> participantes;
}
