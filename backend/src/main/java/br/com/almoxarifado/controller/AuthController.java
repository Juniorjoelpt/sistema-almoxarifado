package br.com.almoxarifado.controller;

import br.com.almoxarifado.dto.LoginRequest;
import br.com.almoxarifado.dto.LoginResponse;
import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.repository.UsuarioRepository;
import br.com.almoxarifado.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository repository;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authManager,
            UsuarioRepository repository,
            JwtService jwtService) {

        this.authManager = authManager;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        try {

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getSenha()
                    )
            );

        } catch (BadCredentialsException e) {

            throw new RuntimeException(
                    "Usuário inexistente ou senha inválida");
        }

        Usuario usuario =
                repository.findByUsername(
                        request.getUsername())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuário não encontrado"));

        if (!Boolean.TRUE.equals(
                usuario.getAtivo())) {

            throw new RuntimeException(
                    "Usuário está desativado");
        }

        String token =
        jwtService.gerarToken(
        usuario.getUsername(),
        usuario.getRole().name(),
        usuario.getPrefeitura() != null
            ? usuario.getPrefeitura().getId()
            : null
    );

        return new LoginResponse(token,usuario.getNome(),
        usuario.getUsername(),
        usuario.getRole().name()
);

    }
}