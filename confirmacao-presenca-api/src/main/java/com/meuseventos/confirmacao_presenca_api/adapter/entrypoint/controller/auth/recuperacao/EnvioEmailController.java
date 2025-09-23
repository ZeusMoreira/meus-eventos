package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.auth.recuperacao;

import com.meuseventos.confirmacao_presenca_api.domain.service.auth.recuperacao.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class EnvioEmailController {

    private final EnvioEmailService service;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<Void> esqueciSenha() {
        service.solicitarResetSenha();
        return ResponseEntity.ok().build();
    }
}
