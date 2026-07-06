package br.com.almoxarifado.dto;

public class LoginResponse {

private String token;
private String nome;
private String username;
private String role;

public LoginResponse(
        String token,
        String nome,
        String username,
        String role) {

    this.token = token;
    this.nome = nome;
    this.username = username;
    this.role = role;
}

public String getToken() {
    return token;
}

public String getNome() {
    return nome;
}

public String getUsername() {
    return username;
}

public String getRole() {
    return role;
}

}
