package br.com.almoxarifado.config;

import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.enums.Role;
import br.com.almoxarifado.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public AdminInitializer(
            UsuarioRepository repository,
            PasswordEncoder encoder) {

        this.repository = repository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void criarAdmin() {

        if (!repository.existsByUsername("admin")) {

            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setUsername("admin");
            admin.setSenha(
                    encoder.encode("admin123"));

            admin.setRole(Role.ROLE_SUPER_ADMIN);
            admin.setAtivo(true);

            repository.save(admin);

            System.out.println(
                    "ADMIN criado com sucesso");
        }
    }
}