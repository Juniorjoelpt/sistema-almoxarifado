package br.com.almoxarifado.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_sistema")
public class LogSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private String acao;

    private String entidade;

    private LocalDateTime dataHora;
    
 

    @Column(length = 2000)
    private String detalhes;
    
    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    // getters e setters
   public Long getId() {
    return id;
}

public String getUsuario() {
    return usuario;
}

public void setUsuario(String usuario) {
    this.usuario = usuario;
}

public String getAcao() {
    return acao;
}

public void setAcao(String acao) {
    this.acao = acao;
}

public String getEntidade() {
    return entidade;
}

public void setEntidade(String entidade) {
    this.entidade = entidade;
}

public LocalDateTime getDataHora() {
    return dataHora;
}

public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
}

public String getDetalhes() {
    return detalhes;
}

public void setDetalhes(String detalhes) {
    this.detalhes = detalhes;
}


public Prefeitura getPrefeitura() {
    return prefeitura;
}

public void setPrefeitura(Prefeitura prefeitura) {
    this.prefeitura = prefeitura;
}
}