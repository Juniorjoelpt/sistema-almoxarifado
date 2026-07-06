package br.com.almoxarifado.entity;

import br.com.almoxarifado.enums.TipoMovimentacao;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "movimentacoes",
    indexes = {
        @Index(name = "idx_mov_prefeitura",
               columnList = "prefeitura_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
    
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    private Double quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    private String observacao;

    private LocalDateTime dataMovimentacao;
    
    @Transient
    private String numeroLote;

    @Transient
    private LocalDate dataValidade;
}