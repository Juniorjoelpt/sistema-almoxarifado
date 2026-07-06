package br.com.almoxarifado.security;

import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.repository.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository repository;

    public CustomUserDetailsService(
            UsuarioRepository repository) {

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        Usuario usuario =
                repository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Usuário inexistente ou senha inválida"));

        if (usuario.getAtivo() == null ||
                !usuario.getAtivo()) {

            throw new UsernameNotFoundException(
                    "Usuário está desativado");
        }

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha())
                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(
                                        usuario.getRole().name()
                                )
                        )
                )
                .build();
    }
}