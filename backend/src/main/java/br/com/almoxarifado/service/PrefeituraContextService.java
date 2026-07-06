package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Prefeitura;
import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.enums.Role;
import br.com.almoxarifado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import br.com.almoxarifado.repository.PrefeituraRepository;

@Service
public class PrefeituraContextService {

    private final UsuarioRepository usuarioRepository;
    private final PrefeituraRepository prefeituraRepository;
    @Autowired
    private HttpServletRequest request;

    public PrefeituraContextService(
            UsuarioRepository usuarioRepository,
            PrefeituraRepository prefeituraRepository,
            HttpServletRequest request) {

        this.usuarioRepository = usuarioRepository;
         this.prefeituraRepository = prefeituraRepository;
          this.request = request;
    }

    public Prefeitura getPrefeituraAtual() {

    Usuario usuario = getUsuarioAtual();

    // usuário comum
    if (usuario.getRole() != Role.ROLE_SUPER_ADMIN) {

        return usuario.getPrefeitura();

    }

    // SUPER_ADMIN
    String prefeituraHeader =
            request.getHeader("X-Prefeitura-Id");

    if (prefeituraHeader == null ||
        prefeituraHeader.isBlank()) {

        return usuario.getPrefeitura();
    }

    Long prefeituraId =
            Long.valueOf(prefeituraHeader);

    return prefeituraRepository
            .findById(prefeituraId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Prefeitura não encontrada"));
}

   public Long getPrefeituraIdAtual() {

    System.out.println("ROLE: " + getUsuarioAtual().getRole());

    System.out.println(
        "HEADER X-Prefeitura-Id = "
        + request.getHeader("X-Prefeitura-Id")
    );

    if (isSuperAdmin()) {

        String prefeituraHeader =
                request.getHeader("X-Prefeitura-Id");

        if (prefeituraHeader != null &&
                !prefeituraHeader.isBlank()) {

            System.out.println(
                "Prefeitura escolhida: " + prefeituraHeader
            );

            return Long.valueOf(prefeituraHeader);
        }
    }

    Prefeitura prefeitura = getPrefeituraAtual();

    System.out.println(
        "Prefeitura do usuário: " +
        prefeitura.getId()
    );

    return prefeitura.getId();
}
    
    
    public Usuario getUsuarioAtual() {

    String username =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    return usuarioRepository
            .findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Usuário não encontrado"));
}
    
    public boolean isSuperAdmin() {

    return getUsuarioAtual().getRole()
            == Role.ROLE_SUPER_ADMIN;
}
    
    
}