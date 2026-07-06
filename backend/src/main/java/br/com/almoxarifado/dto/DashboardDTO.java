package br.com.almoxarifado.dto;

public class DashboardDTO {

    private Long totalProdutos;
    private Long totalFornecedores;
    private Long totalSetores;
    private Long estoqueBaixo;
    private Long totalMovimentacoes;
    private Double entradasMes;

private Double saidasMes;

    public Long getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(Long totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public Long getTotalFornecedores() {
        return totalFornecedores;
    }

    public void setTotalFornecedores(Long totalFornecedores) {
        this.totalFornecedores = totalFornecedores;
    }

    public Long getTotalSetores() {
        return totalSetores;
    }

    public void setTotalSetores(Long totalSetores) {
        this.totalSetores = totalSetores;
    }

    public Long getEstoqueBaixo() {
        return estoqueBaixo;
    }

    public void setEstoqueBaixo(Long estoqueBaixo) {
        this.estoqueBaixo = estoqueBaixo;
    }

    public Long getTotalMovimentacoes() {
        return totalMovimentacoes;
    }

    public void setTotalMovimentacoes(Long totalMovimentacoes) {
        this.totalMovimentacoes = totalMovimentacoes;
    }
    public Double getEntradasMes() {
    return entradasMes;
}

public void setEntradasMes(Double entradasMes) {
    this.entradasMes = entradasMes;
}

public Double getSaidasMes() {
    return saidasMes;
}

public void setSaidasMes(Double saidasMes) {
    this.saidasMes = saidasMes;
}
}