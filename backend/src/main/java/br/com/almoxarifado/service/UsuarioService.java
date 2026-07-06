package br.com.almoxarifado.service;

import br.com.almoxarifado.dto.AlterarSenhaDTO;
import br.com.almoxarifado.dto.UsuarioDTO;
import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.enums.Role;
import br.com.almoxarifado.repository.PrefeituraRepository;
import br.com.almoxarifado.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import br.com.almoxarifado.entity.Prefeitura;




import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final PrefeituraRepository prefeituraRepository;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder,PrefeituraRepository prefeituraRepository) {
        
        
        this.repository = repository;
        this.encoder = encoder;
        this.prefeituraRepository = prefeituraRepository;

    }

    // Cria um novo usuário (apenas ADMIN)
    public Usuario criar(UsuarioDTO dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());             // obrigatório
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(encoder.encode(dto.getSenha()));
        usuario.setRole(dto.getRole());
        
       
       if (dto.getRole() != Role.ROLE_SUPER_ADMIN
        && dto.getPrefeituraId() == null) {

    throw new RuntimeException(
            "Usuário deve possuir prefeitura");
}
        if (dto.getPrefeituraId() != null) {

    Prefeitura prefeitura =
            prefeituraRepository
                    .findById(dto.getPrefeituraId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Prefeitura não encontrada"));

    usuario.setPrefeitura(prefeitura);
}

        return repository.save(usuario);
    }

    // Lista todos os usuários
    public List<Usuario> listarTodos() {

    Usuario logado = obterUsuarioLogado();

    if (logado.getRole() == Role.ROLE_SUPER_ADMIN) {
        return repository.findAll();
    }

    return repository.findByPrefeituraId(
            logado.getPrefeitura().getId());
}

    // Atualiza um usuário existente
    public Usuario atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setUsername(dto.getUsername());
        usuario.setRole(dto.getRole());
        
        
       
        
        Prefeitura prefeitura = prefeituraRepository
        .findById(dto.getPrefeituraId())
        .orElseThrow(() ->
                new RuntimeException("Prefeitura não encontrada"));

usuario.setPrefeitura(prefeitura);

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(encoder.encode(dto.getSenha()));
        }
        if (dto.getRole() != Role.ROLE_SUPER_ADMIN
        && dto.getPrefeituraId() == null) {

    throw new RuntimeException(
            "Usuário deve possuir prefeitura");
}
        
        if (usuario.getRole() == Role.ROLE_SUPER_ADMIN
        && dto.getRole() != Role.ROLE_SUPER_ADMIN) {

    long total =
            repository.countByRoleAndAtivoTrue(
                    Role.ROLE_SUPER_ADMIN);

    if (total <= 1) {

        throw new RuntimeException(
                "Não é permitido remover o último SUPER_ADMIN");
    }
    repository.findByUsername(dto.getUsername())
    .ifPresent(u -> {

        if(!u.getId().equals(id)){

            throw new RuntimeException(
                "Username já utilizado."
            );

        }

    });
}

        return repository.save(usuario);
    }

    // Ativa ou desativa um usuário
    public Usuario alterarAtivo(Long id, boolean ativo) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(ativo);
        
        if (!ativo
        && usuario.getRole() == Role.ROLE_SUPER_ADMIN) {

    long adminsAtivos =
            repository.countByRoleAndAtivoTrue(
                    Role.ROLE_SUPER_ADMIN);

    if (adminsAtivos <= 1) {

        throw new RuntimeException(
                "Não é permitido desativar o último SUPER_ADMIN");
    }
}

usuario.setAtivo(ativo);

return repository.save(usuario);
    }

    // Deleta um usuário
    public void deletar(Long id) {

    Usuario usuario = repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Usuário não encontrado"));

    if (usuario.getRole() == Role.ROLE_SUPER_ADMIN) {

        long total =
                repository.countByRoleAndAtivoTrue(
                        Role.ROLE_SUPER_ADMIN);

        if (total <= 1) {

            throw new RuntimeException(
                    "Não é permitido excluir o último SUPER_ADMIN");
        }
    }

    repository.delete(usuario);
}

    // Desativa um usuário com validação para não desativar último ADMIN
    public void desativar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getRole() == Role.ROLE_SUPER_ADMIN) {

    long adminsAtivos =
            repository.countByRoleAndAtivoTrue(
                    Role.ROLE_SUPER_ADMIN);

     if (adminsAtivos <= 1) {

        throw new RuntimeException(
                "Não é permitido desativar o último SUPER_ADMIN");
    }
}

        usuario.setAtivo(false);
        repository.save(usuario);
    }
    public void alterarMinhaSenha(
        AlterarSenhaDTO dto) {

    String username =
        SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

Usuario usuario =
        repository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"));

if (!Boolean.TRUE.equals(usuario.getAtivo())) {

    throw new RuntimeException(
            "Usuário desativado");
}



    if (!encoder.matches(
            dto.getSenhaAtual(),
            usuario.getSenha())) {

        throw new RuntimeException(
                "Senha atual inválida");
    }

    if (dto.getNovaSenha().length() < 6) {

    throw new RuntimeException(
            "Senha deve possuir pelo menos 6 caracteres");
}

usuario.setSenha(
        encoder.encode(
                dto.getNovaSenha()));

repository.save(usuario);
}
    public Usuario obterUsuarioLogado() {

    String username =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    return repository.findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Usuário não encontrado"));
}
        public Usuario buscarPorId(Long id) {

    return repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Usuário não encontrado"));
}
    
}