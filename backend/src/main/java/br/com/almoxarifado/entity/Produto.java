package br.com.almoxarifado.entity;

import jakarta.persistence.*;
import br.com.almoxarifado.entity.Categoria;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "produtos",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "codigo",
                "prefeitura_id"
            }
        )
    }
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    private String nome;


    private String unidade;
    
    @Column(name = "unidade_compra")
    private String unidadeCompra;
    
    private String unidadeEstoque;

    private String unidadeEmbalagem;

    private Double fatorConversao;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private Double estoqueAtual = 0.0;

    @Column(nullable = false)
    private Double estoqueMinimo = 0.0;

    @Column(nullable = false)
    private Boolean ativo = true;

    private LocalDateTime dataCadastro;

    private LocalDateTime dataAtualizacao;
    
    
    @Column(nullable = false)
    private Double valorUnitario = 0.0;

    
    
    private LocalDate dataValidade;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    @PrePersist
    public void prePersist() {

        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();

        if (this.estoqueAtual == null) {
            this.estoqueAtual = 0.0;
        }

        if (this.estoqueMinimo == null) {
            this.estoqueMinimo = 0.0;
        }

        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   public Categoria getCategoria() {
    return categoria;
}

    public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
}

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public Double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    public LocalDate getDataValidade() {
    return dataValidade;
}

    public void setDataValidade(LocalDate dataValidade) {
    this.dataValidade = dataValidade;
}
    public Prefeitura getPrefeitura() {
    return prefeitura;
}

    public void setPrefeitura(Prefeitura prefeitura) {
    this.prefeitura = prefeitura;
}
    
    public String getUnidadeEstoque() {
    return unidadeEstoque;
}

    public void setUnidadeEstoque(String unidadeEstoque) {
    this.unidadeEstoque = unidadeEstoque;
}

    public String getUnidadeEmbalagem() {
    return unidadeEmbalagem;
}

    public void setUnidadeEmbalagem(String unidadeEmbalagem) {
    this.unidadeEmbalagem = unidadeEmbalagem;
}

    public Double getFatorConversao() {
    return fatorConversao;
}

    public void setFatorConversao(Double fatorConversao) {
    this.fatorConversao = fatorConversao;
}
    
    public String getUnidadeCompra() {
    return unidadeCompra;
}

    public void setUnidadeCompra(String unidadeCompra) {
    this.unidadeCompra = unidadeCompra;
}
   
}