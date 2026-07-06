package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Fornecedor;
import br.com.almoxarifado.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/fornecedores")
@CrossOrigin("*")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Fornecedor salvar(@RequestBody Fornecedor fornecedor) {
        return service.salvar(fornecedor);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Fornecedor> listar() {
        return service.listarTodos();
    }
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Page<Fornecedor> listarPaginado(
        Pageable pageable) {

    return service.listarPaginado(pageable);
}
    @GetMapping("/filtro/razao-social")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Fornecedor> filtrarPorRazaoSocial(
        @RequestParam String nome) {

    return service.filtrarPorRazaoSocial(nome);
}

    @GetMapping("/filtro/cnpj")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Fornecedor> filtrarPorCnpj(
        @RequestParam String cnpj) {

    return service.filtrarPorCnpj(cnpj);
}

    @GetMapping("/filtro/ativo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Fornecedor> filtrarPorAtivo(
        @RequestParam Boolean ativo) {

    return service.filtrarPorAtivo(ativo);
}

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Fornecedor buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Fornecedor atualizar(
        @PathVariable Long id,
        @RequestBody Fornecedor fornecedor) {

    return service.atualizar(id, fornecedor);
}

}