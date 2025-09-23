package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> errors) {
    public static ErroResposta respostaPadrao(String mensagem) {
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem) {
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

    public static ErroResposta naoEncontrado(String mensagem) {
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

    public static ErroResposta entidadeNaoProcessada(String mensagem, List<ErroCampo> errors) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), mensagem, errors);
    }

    public static ErroResposta erroInterno(String mensagem) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), mensagem, List.of());
    }

    public static ErroResposta naoAutorizado(String mensagem) {
        return new ErroResposta(HttpStatus.UNAUTHORIZED.value(), mensagem, List.of());
    }

    public static ErroResposta semPermissao(String mensagem) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), mensagem, List.of());
    }
}
