package com.meuseventos.confirmacao_presenca_api.domain.service.auth.recuperacao;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.adminUser.AdminUserRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.RedefinicaoSenhaDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.AdminUserEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.CredenciaisInvalidasException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.validation.RecuperacaoSenhaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedefinicaoSenhaService {

    private final AdminUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RecuperacaoSenhaValidator validator;

    public void redefinirSenha(RedefinicaoSenhaDTO dto) {
        AdminUserEntity usuario = validator.validar(dto);

        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setResetToken(null);
        usuario.setResetExpiracao(null);
        repository.save(usuario);
    }
}

