package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.evento;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.CriarEventoInputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.EventoDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.service.evento.criar.CriarEventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/eventos")
public class CriarEventoController {

    private final CriarEventoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CriarEventoInputDTO evento) {
        EventoEntity output = service.salvar(evento);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
