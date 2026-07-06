package br.com.almoxarifado.dto;

import br.com.almoxarifado.enums.Role;

public class UsuarioDTO {

    private String nome;
    private String username;
    private String senha;
    private Role role;
    private Long prefeituraId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public Long getPrefeituraId() {
    return prefeituraId;
}

    public void setPrefeituraId(Long prefeituraId) {
    this.prefeituraId = prefeituraId;
    
    
}
}