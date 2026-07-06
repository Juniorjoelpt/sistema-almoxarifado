package br.com.almoxarifado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse>
    tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException ex) {

        ErrorResponse erro =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(
            EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponse>
    tratarEstoqueInsuficiente(
            EstoqueInsuficienteException ex) {

        ErrorResponse erro =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>
    tratarRuntimeException(
            RuntimeException ex) {

        ErrorResponse erro =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    tratarException(
            Exception ex) {

        ErrorResponse erro =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno do servidor");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}