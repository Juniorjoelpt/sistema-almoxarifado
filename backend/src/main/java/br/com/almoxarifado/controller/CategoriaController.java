package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Categoria;
import br.com.almoxarifado.service.CategoriaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(
            CategoriaService service) {

        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Categoria salvar(
            @RequestBody Categoria categoria) {

        return service.salvar(categoria);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Categoria> listar() {

        return service.listarTodas();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Categoria buscar(
            @PathVariable Long id) {

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Categoria atualizar(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {

        return service.atualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public void excluir(
            @PathVariable Long id) {

        service.excluir(id);
    }
}