package br.com.almoxarifado.controller;

import br.com.almoxarifado.dto.UsuarioDTO;
import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.service.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.almoxarifado.dto.AlterarSenhaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Endpoint para o ADMIN criar novos usuários
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Usuario criarUsuario(@RequestBody UsuarioDTO dto) {
        return service.criar(dto);
    }

    // Endpoint para listar todos os usuários (apenas ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Usuario> listarUsuarios() {
        return service.listarTodos();
    }

    // Endpoint para atualizar usuário existente (apenas ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Usuario atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioDTO dto) {
        return service.atualizar(id, dto);
    }

    // Endpoint para ativar/desativar usuário (apenas ADMIN)
    @PatchMapping("/{id}/ativo")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Usuario alterarAtivo(
            @PathVariable Long id,
            @RequestParam boolean ativo) {
        return service.alterarAtivo(id, ativo);
    }

    // Endpoint para desativar usuário (apenas ADMIN)
    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void desativarUsuario(@PathVariable Long id) {
        service.desativar(id);
    }

    // Endpoint para deletar usuário (apenas ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deletarUsuario(@PathVariable Long id) {
        service.deletar(id);
    }
    @PutMapping("/minha-senha")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public String alterarMinhaSenha(
        @RequestBody AlterarSenhaDTO dto) {

    service.alterarMinhaSenha(dto);

    return "Senha alterada com sucesso";
}
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Usuario usuarioLogado() {

    return service.obterUsuarioLogado();
}
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Usuario buscarPorId(
        @PathVariable Long id) {

    return service.buscarPorId(id);
}

}