package br.com.almoxarifado.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime dataHora;
    private Integer status;
    private String erro;

    public ErrorResponse() {
    }

    public ErrorResponse(
            LocalDateTime dataHora,
            Integer status,
            String erro) {

        this.dataHora = dataHora;
        this.status = status;
        this.erro = erro;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}