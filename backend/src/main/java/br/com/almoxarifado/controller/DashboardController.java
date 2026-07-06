package br.com.almoxarifado.controller;

import br.com.almoxarifado.dto.DashboardDTO;
import br.com.almoxarifado.dto.ProdutoMovimentacaoDTO;
import br.com.almoxarifado.entity.Movimentacao;
import br.com.almoxarifado.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.almoxarifado.entity.Produto;
import java.util.List;
import br.com.almoxarifado.dto.MovimentacaoMensalDTO;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(
            DashboardService service) {

        this.service = service;
    }

    @GetMapping("/resumo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public DashboardDTO resumo() {

        return service.obterResumo();
    }
    @GetMapping("/estoque-baixo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> estoqueBaixo() {

    return service.listarEstoqueBaixo();
}
    @GetMapping("/top-produtos")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<ProdutoMovimentacaoDTO>
    topProdutos() {

    return service.listarTopProdutos();
}
    @GetMapping("/movimentacoes-mes")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Long movimentacoesMes() {

    return service.movimentacoesMesAtual();
}
    @GetMapping("/entradas-mes")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Double entradasMes() {

    return service.entradasMesAtual();
}
    @GetMapping("/saidas-mes")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public Double saidasMes() {

    return service.saidasMesAtual();
}
    @GetMapping("/ultimas-movimentacoes")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Movimentacao> ultimasMovimentacoes() {

    return service.ultimasMovimentacoes();
}
    @GetMapping("/ultimos-12-meses")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<MovimentacaoMensalDTO>
    ultimos12Meses() {

    return service.listarUltimos12Meses();
}
    @GetMapping("/proximos-vencimento")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public List<Produto> produtosProximosVencimento() {

    return service
            .listarProdutosProximosVencimento();
}
}