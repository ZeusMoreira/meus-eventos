package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.evento;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarListagemPaginadaEventosOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.PaginacaoDTO;
import com.meuseventos.confirmacao_presenca_api.domain.service.evento.listar.BuscarListagemPaginadaEventosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/eventos")
@RequiredArgsConstructor
public class BuscarListagemPaginadaEventosController {

    private final BuscarListagemPaginadaEventosService service;

    @GetMapping
    public ResponseEntity<PaginacaoDTO<BuscarListagemPaginadaEventosOutputDTO>> listarEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String filtro) {

        Page<BuscarListagemPaginadaEventosOutputDTO> eventos = service.listarEventos(page, size, filtro);

        PaginacaoDTO<BuscarListagemPaginadaEventosOutputDTO> resposta = new PaginacaoDTO<>(
                eventos.getContent(),
                eventos.getTotalElements(),
                eventos.getTotalPages(),
                eventos.getNumberOfElements()
        );

        return ResponseEntity.ok(resposta);
    }

}