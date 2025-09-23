package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.participante;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.ParticipanteInputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import com.meuseventos.confirmacao_presenca_api.domain.service.participante.criar.CriarParticipanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eventos")
public class CriarParticipanteController {
    private final CriarParticipanteService service;

    @PostMapping("{codigo}/participantes")
    public ResponseEntity<Object> salvar(@RequestBody @Valid ParticipanteInputDTO participante, @PathVariable("codigo") String codigo) {
        ParticipanteEntity output = service.salvar(participante, codigo);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }
}
