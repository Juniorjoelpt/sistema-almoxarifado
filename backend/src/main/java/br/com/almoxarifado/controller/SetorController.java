package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Setor;
import br.com.almoxarifado.service.SetorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/setores")
@CrossOrigin("*")
public class SetorController {

    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Setor salvar(@RequestBody Setor setor) {
        return service.salvar(setor);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Setor> listar() {
        return service.listarTodos();
    }
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Page<Setor> listarPaginado(
        Pageable pageable) {

    return service.listarPaginado(pageable);
}
    @GetMapping("/filtro/nome")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Setor> filtrarPorNome(
        @RequestParam String nome) {

    return service.filtrarPorNome(nome);
}

    @GetMapping("/filtro/responsavel")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Setor> filtrarPorResponsavel(
        @RequestParam String responsavel) {

    return service
            .filtrarPorResponsavel(responsavel);
}

    @GetMapping("/filtro/ativo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Setor> filtrarPorAtivo(
        @RequestParam Boolean ativo) {

    return service.filtrarPorAtivo(ativo);
}

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Setor buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
public Setor atualizar(
        @PathVariable Long id,
        @RequestBody Setor setor) {

    return service.atualizar(id, setor);
}
}