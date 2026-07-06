package br.com.almoxarifado.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fornecedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String razaoSocial;

    private String nomeFantasia;

    @Column
    private String cnpj;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    private String telefone;

    private String email;

    private String endereco;

    private Boolean ativo;
    
    
}