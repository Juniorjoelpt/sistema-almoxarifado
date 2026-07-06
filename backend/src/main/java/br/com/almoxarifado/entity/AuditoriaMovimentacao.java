package br.com.almoxarifado.entity;

import br.com.almoxarifado.enums.TipoMovimentacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_movimentacoes")
public class AuditoriaMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    private String usuario;

    private String produto;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private Double quantidade;

    private LocalDateTime dataHora;

    @Column(length = 1000)
    private String observacao;

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Prefeitura getPrefeitura() {
    return prefeitura;
}

    public void setPrefeitura(Prefeitura prefeitura) {
    this.prefeitura = prefeitura;
}
}