package br.com.almoxarifado.dto;

public class ProdutoMovimentacaoDTO {

    private String produto;
    private Long total;

    public ProdutoMovimentacaoDTO(
            String produto,
            Long total) {

        this.produto = produto;
        this.total = total;
    }

    public String getProduto() {
        return produto;
    }

    public Long getTotal() {
        return total;
    }
}