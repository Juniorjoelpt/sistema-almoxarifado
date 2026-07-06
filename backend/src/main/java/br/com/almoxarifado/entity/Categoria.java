package br.com.almoxarifado.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "categorias",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "nome",
                "prefeitura_id"
            }
        )
    }
)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    @Column
    private String nome;

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Prefeitura getPrefeitura() {
    return prefeitura;
}

    public void setPrefeitura(Prefeitura prefeitura) {
    this.prefeitura = prefeitura;
}
}