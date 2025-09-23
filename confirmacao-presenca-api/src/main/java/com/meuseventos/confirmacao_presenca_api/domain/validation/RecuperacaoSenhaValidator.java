package com.meuseventos.confirmacao_presenca_api.domain.validation;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.adminUser.AdminUserRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.RedefinicaoSenhaDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.AdminUserEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.CredenciaisInvalidasException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecuperacaoSenhaValidator {

    private final AdminUserRepository repository;

    public AdminUserEntity validar(RedefinicaoSenhaDTO dto) {
        if(dto.getToken() == null) {
            throw new CredenciaisInvalidasException("Nenhum token de redefinição informado.");
        }

        AdminUserEntity usuario = repository.findByResetToken(dto.getToken())
                .orElseThrow(() -> new NaoEncontradoException("O Token é inválido."));

        if (usuario.getResetExpiracao().isBefore(LocalDateTime.now())) {
            throw new CredenciaisInvalidasException("Essa requisição expirou, tente novamente.");
        }

        return usuario;
    }
}
