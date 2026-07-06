package br.com.almoxarifado.dto;

public class MovimentacaoMensalDTO {

    private String mes;
    private Double entradas;
    private Double saidas;

    public MovimentacaoMensalDTO(
            String mes,
            Double entradas,
            Double saidas) {

        this.mes = mes;
        this.entradas = entradas;
        this.saidas = saidas;
    }

    public String getMes() {
        return mes;
    }

    public Double getEntradas() {
        return entradas;
    }

    public Double getSaidas() {
        return saidas;
    }
}