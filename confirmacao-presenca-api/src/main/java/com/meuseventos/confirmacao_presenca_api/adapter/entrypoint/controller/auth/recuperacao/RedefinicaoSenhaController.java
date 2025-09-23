package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.auth.recuperacao;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.RedefinicaoSenhaDTO;
import com.meuseventos.confirmacao_presenca_api.domain.service.auth.recuperacao.RedefinicaoSenhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RedefinicaoSenhaController {

    private final RedefinicaoSenhaService service;

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid RedefinicaoSenhaDTO dto) {
        service.redefinirSenha(dto);
        return ResponseEntity.ok().build();
    }
}
