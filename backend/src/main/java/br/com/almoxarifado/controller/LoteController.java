package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Lote;
import br.com.almoxarifado.service.LoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/lotes")
@CrossOrigin("*")
public class LoteController {

    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Lote salvar(
            @RequestBody Lote lote) {

        return service.salvar(lote);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Lote> listar() {

        return service.listarTodos();
    }
    @GetMapping("/produto/{produtoId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Lote> listarPorProduto(
        @PathVariable Long produtoId) {

    return service.listarPorProduto(
            produtoId);
}
}