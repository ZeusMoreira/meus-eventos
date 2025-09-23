package com.meuseventos.confirmacao_presenca_api.domain.exception;

public class QrCodeException extends RuntimeException {
    public QrCodeException(String message) {
        super(message);
    }
}
