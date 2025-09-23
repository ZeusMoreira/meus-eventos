package com.meuseventos.confirmacao_presenca_api.domain.exception;

public class CpfInvalidoException extends RuntimeException {
    public CpfInvalidoException(String message) {
        super(message);
    }
}
