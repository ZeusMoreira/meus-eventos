package com.meuseventos.confirmacao_presenca_api.domain.service.auth.login;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.adminUser.AdminUserRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.LoginDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.AdminUserEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.CredenciaisInvalidasException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void logar(LoginDTO credenciais) {
        AdminUserEntity usuario = repository.findByEmail(credenciais.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("Email não encontrado."));

        if (!passwordEncoder.matches(credenciais.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Credenciais inválidas.");
        }
    }
}
