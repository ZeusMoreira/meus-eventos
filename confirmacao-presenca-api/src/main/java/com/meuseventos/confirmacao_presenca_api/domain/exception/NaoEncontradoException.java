package com.meuseventos.confirmacao_presenca_api.domain.exception;

public class NaoEncontradoException extends RuntimeException{
    public NaoEncontradoException(String message) {
        super(message);
    }
}
