package br.com.almoxarifado.dto;

import java.time.LocalDate;

public class EntradaProdutoDTO {

    private Long produtoId;

    private Long fornecedorId;

    private Double quantidade;

    private String observacao;

    private String numeroLote;

    private LocalDate dataValidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }


    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }
    public Long getFornecedorId() {
    return fornecedorId;
}

    public void setFornecedorId(Long fornecedorId) {
    this.fornecedorId = fornecedorId;
}
}