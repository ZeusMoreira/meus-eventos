package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.participante;

import com.meuseventos.confirmacao_presenca_api.domain.service.participante.excluir.ExcluirParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/eventos")
public class ExcluirParticipanteController {

    private final ExcluirParticipanteService service;

    @DeleteMapping("/{codigo}/participantes/{id}")
    public ResponseEntity<Object> excluir(@PathVariable("codigo") String codigo, @PathVariable("id") String id) {
        service.excluir(codigo, id);
        return ResponseEntity.noContent().build();
    }
}
