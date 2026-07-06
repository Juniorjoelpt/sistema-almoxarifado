package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Movimentacao;
import br.com.almoxarifado.enums.TipoMovimentacao;
import br.com.almoxarifado.service.MovimentacaoService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.almoxarifado.dto.EntradaProdutoDTO;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin("*")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public Movimentacao salvar(@RequestBody Movimentacao movimentacao) {
        return service.salvar(movimentacao);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Movimentacao> listar() {
        return service.listarTodas();
    }
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Page<Movimentacao> listarPaginado(
        Pageable pageable) {

    return service.listarPaginado(pageable);
}
    @GetMapping("/filtro/tipo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Movimentacao> listarPorTipo(
        @RequestParam TipoMovimentacao tipo) {

    return service.listarPorTipo(tipo);
}
    @GetMapping("/filtro/produto/{produtoId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> listarPorProduto(
        @PathVariable Long produtoId) {

    return service.listarPorProduto(produtoId);
}
@GetMapping("/filtro/periodo")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> listarPorPeriodo(
        @RequestParam LocalDateTime inicio,
        @RequestParam LocalDateTime fim) {

    return service.listarPorPeriodo(
            inicio,
            fim);
}
@GetMapping("/filtro/entradas")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> entradasPeriodo(
        @RequestParam LocalDateTime inicio,
        @RequestParam LocalDateTime fim) {

    return service
            .listarEntradasPorPeriodo(
                    inicio,
                    fim);
}
@GetMapping("/filtro/saidas")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> saidasPeriodo(
        @RequestParam LocalDateTime inicio,
        @RequestParam LocalDateTime fim) {

    return service
            .listarSaidasPorPeriodo(
                    inicio,
                    fim);
}

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Movimentacao buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    

@GetMapping("/entradas")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> listarEntradas(
        @RequestParam LocalDateTime inicio,
        @RequestParam LocalDateTime fim) {

    return service.listarEntradasPorPeriodo(
            inicio,
            fim);
}

@GetMapping("/saidas")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public List<Movimentacao> listarSaidas(
        @RequestParam LocalDateTime inicio,
        @RequestParam LocalDateTime fim) {

    return service.listarSaidasPorPeriodo(
            inicio,
            fim);
}
@PostMapping("/entrada")
@PreAuthorize(
    "hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')"
)
public Movimentacao registrarEntrada(
        @RequestBody EntradaProdutoDTO dto) {

    return service.registrarEntrada(dto);
}
}