package com.meuseventos.confirmacao_presenca_api.domain.exception;

public class ConflitoException extends RuntimeException{
    public ConflitoException(String message) {
        super(message);
    }
}

