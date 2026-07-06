package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Movimentacao;
import br.com.almoxarifado.entity.Produto;
import br.com.almoxarifado.service.MovimentacaoService;
import br.com.almoxarifado.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/produtos")

@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService service;
    private final MovimentacaoService movimentacaoService;

    public ProdutoController(
        ProdutoService service,
        MovimentacaoService movimentacaoService) {

    this.service = service;
    this.movimentacaoService = movimentacaoService;
}

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Produto salvar(
        @RequestBody Produto produto) {

    return service.salvar(produto);
}

    @GetMapping
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> listar() {
        return service.listarTodos();
    }
    @GetMapping("/paginado")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Page<Produto> listarPaginado(
        Pageable pageable) {

    return service.listarPaginado(pageable);
}
    @GetMapping("/relatorios/estoque-baixo")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> estoqueBaixo() {
    return service.listarEstoqueBaixo();
}
    @GetMapping("/filtro")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> filtrarPorNome(
        @RequestParam String nome) {

    return service.filtrarPorNome(nome);
}
    @GetMapping("/filtro/codigo")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> filtrarPorCodigo(
        @RequestParam String codigo) {

    return service.filtrarPorCodigo(codigo);
}

    @GetMapping("/filtro/categoria")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> filtrarPorCategoria(
        @RequestParam String categoria) {

    return service.filtrarPorCategoria(categoria);
}

    @GetMapping("/filtro/ativo")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> filtrarPorAtivo(
        @RequestParam Boolean ativo) {

    return service.filtrarPorAtivo(ativo);
}

    @GetMapping("/{id}")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Produto buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
     @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    @PutMapping("/{id}")
     @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Produto atualizar(
        @PathVariable Long id,
        @RequestBody Produto produto) {

    return service.atualizar(id, produto);
}
    @GetMapping("/{id}/historico")
     @PreAuthorize( "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Movimentacao> historico(@PathVariable Long id) {
    return movimentacaoService.listarPorProduto(id);
}
    
}
