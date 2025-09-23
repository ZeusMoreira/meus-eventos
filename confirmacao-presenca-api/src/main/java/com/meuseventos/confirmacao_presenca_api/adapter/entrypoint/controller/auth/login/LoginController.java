package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.auth.login;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.LoginDTO;
import com.meuseventos.confirmacao_presenca_api.domain.service.auth.JwtService;
import com.meuseventos.confirmacao_presenca_api.domain.service.auth.login.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final JwtService jwtService;
    private final LoginService service;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginDTO credenciais) {
        service.logar(credenciais);

        String token = jwtService.gerarToken(credenciais.getEmail());
        return Map.of("token", token);
    }
}
