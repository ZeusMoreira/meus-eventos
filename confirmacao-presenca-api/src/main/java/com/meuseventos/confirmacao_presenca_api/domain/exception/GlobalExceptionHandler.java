package com.meuseventos.confirmacao_presenca_api.domain.exception;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.ErroCampo;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleUnexpected(Exception ex) {
        log.error("Erro inesperado capturado no GlobalExceptionHandler", ex);
        return ErroResposta.erroInterno("Ocorreu um erro inesperado. Tente novamente mais tarde.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();

        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return ErroResposta.entidadeNaoProcessada("Os campos obrigatórios devem ser preenchidos!", listaErros);
    }

    @ExceptionHandler(UuidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleUuid(UuidException ex) {
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleNaoEncontrado(NaoEncontradoException ex) {
        return ErroResposta.naoEncontrado(ex.getMessage());
    }

    @ExceptionHandler(ConflitoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleConflito(ConflitoException ex) {
        return ErroResposta.conflito(ex.getMessage());
    }

    @ExceptionHandler(CpfInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleCpfInvalido(RuntimeException ex) {
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        return ErroResposta.naoAutorizado(ex.getMessage());
    }

}
