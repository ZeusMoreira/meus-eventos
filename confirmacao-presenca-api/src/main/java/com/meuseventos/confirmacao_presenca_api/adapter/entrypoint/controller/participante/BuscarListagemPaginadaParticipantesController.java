package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.participante;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarListagemPaginadaParticipantesOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.PaginacaoDTO;
import com.meuseventos.confirmacao_presenca_api.domain.service.participante.listar.BuscarListagemPaginadaParticipantesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/eventos")
@RequiredArgsConstructor
public class BuscarListagemPaginadaParticipantesController {
    private final BuscarListagemPaginadaParticipantesService service;

    @GetMapping("/{codigo}/participantes")
    public ResponseEntity<PaginacaoDTO<BuscarListagemPaginadaParticipantesOutputDTO>> listarParticipantes(
            @PathVariable("codigo") String codigo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filtro) {

        Page<BuscarListagemPaginadaParticipantesOutputDTO> participantes = service.listarParticipantes(codigo, page, size, filtro);

        PaginacaoDTO<BuscarListagemPaginadaParticipantesOutputDTO> resposta = new PaginacaoDTO<>(
                participantes.getContent(),
                participantes.getTotalElements(),
                participantes.getTotalPages(),
                participantes.getNumberOfElements()
        );

        return ResponseEntity.ok(resposta);
    }
}
