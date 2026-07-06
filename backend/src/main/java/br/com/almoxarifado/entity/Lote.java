package br.com.almoxarifado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    private String numeroLote;

    private Double quantidade;

    private LocalDate dataValidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}