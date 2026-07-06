package br.com.almoxarifado.service;

import br.com.almoxarifado.dto.DashboardDTO;
import br.com.almoxarifado.dto.ProdutoMovimentacaoDTO;
import br.com.almoxarifado.entity.Movimentacao;
import br.com.almoxarifado.repository.FornecedorRepository;
import br.com.almoxarifado.repository.MovimentacaoRepository;
import br.com.almoxarifado.repository.ProdutoRepository;
import br.com.almoxarifado.repository.SetorRepository;
import br.com.almoxarifado.entity.Produto;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Service;
import br.com.almoxarifado.enums.TipoMovimentacao;
import br.com.almoxarifado.dto.MovimentacaoMensalDTO;
import java.time.Month;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class DashboardService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final SetorRepository setorRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final PrefeituraContextService prefeituraContextService;

    public DashboardService(
            ProdutoRepository produtoRepository,
            FornecedorRepository fornecedorRepository,
            SetorRepository setorRepository,
            MovimentacaoRepository movimentacaoRepository,
            PrefeituraContextService prefeituraContextService) {

        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.setorRepository = setorRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.prefeituraContextService = prefeituraContextService;
    }

    public DashboardDTO obterResumo() { 
        
        

        

        DashboardDTO dto = new DashboardDTO();

       Long prefeituraId =
        prefeituraContextService.getPrefeituraIdAtual();

dto.setTotalProdutos(
        produtoRepository.countByPrefeituraId(prefeituraId));

dto.setTotalFornecedores(
        fornecedorRepository.countByPrefeituraId(prefeituraId));

dto.setTotalSetores(
        setorRepository.countByPrefeituraId(prefeituraId));

dto.setTotalMovimentacoes(
        movimentacaoRepository.countByPrefeituraId(prefeituraId));

       dto.setEstoqueBaixo(
    (long) produtoRepository
            .buscarEstoqueBaixoPorPrefeitura(
                    prefeituraId)
            .size());
        dto.setEntradasMes(
        entradasMesAtual());

        dto.setSaidasMes(
        saidasMesAtual());

        return dto;
    }
    public List<Produto> listarEstoqueBaixo() {

    return produtoRepository
            .buscarEstoqueBaixoPorPrefeitura(
                    prefeituraContextService
                            .getPrefeituraIdAtual());
}
    public List<ProdutoMovimentacaoDTO>listarTopProdutos() {

    return movimentacaoRepository
        .listarProdutosMaisMovimentadosPorPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual());
}
    public Long movimentacoesMesAtual() {

    YearMonth mesAtual = YearMonth.now();

    LocalDateTime inicio =
            mesAtual.atDay(1)
                    .atStartOfDay();

    LocalDateTime fim =
            mesAtual.atEndOfMonth()
                    .atTime(23,59,59);

    return movimentacaoRepository
        .countByPrefeituraIdAndDataMovimentacaoBetween(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                inicio,
                fim);
}
    public Double entradasMesAtual() {

    YearMonth mesAtual = YearMonth.now();

    return movimentacaoRepository
        .totalMovimentadoMesPorPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                TipoMovimentacao.ENTRADA,
                mesAtual.getYear(),
                mesAtual.getMonthValue());
}

public Double saidasMesAtual() {
    
    

    YearMonth mesAtual = YearMonth.now();

    return movimentacaoRepository
        .totalMovimentadoMesPorPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                TipoMovimentacao.SAIDA,
                mesAtual.getYear(),
                mesAtual.getMonthValue());
}
   public List<Movimentacao> ultimasMovimentacoes() {

    return movimentacaoRepository
        .findTop10ByPrefeituraIdOrderByDataMovimentacaoDesc(
                prefeituraContextService
                        .getPrefeituraIdAtual());
}
   public List<MovimentacaoMensalDTO>
                listarUltimos12Meses() {

    List<MovimentacaoMensalDTO> lista =
            new ArrayList<>();

    YearMonth atual =
            YearMonth.now();

    for (int i = 11; i >= 0; i--) {

        YearMonth mes =
                atual.minusMonths(i);

        Double entradas =
        movimentacaoRepository
                .totalMovimentadoMesPorPrefeitura(
                        prefeituraContextService
                                .getPrefeituraIdAtual(),
                        TipoMovimentacao.ENTRADA,
                        mes.getYear(),
                        mes.getMonthValue());

        Double saidas =
        movimentacaoRepository
                .totalMovimentadoMesPorPrefeitura(
                        prefeituraContextService
                                .getPrefeituraIdAtual(),
                        TipoMovimentacao.SAIDA,
                        mes.getYear(),
                        mes.getMonthValue());

        lista.add(

            new MovimentacaoMensalDTO(

                mes.getMonth()
                .getDisplayName(
                 TextStyle.SHORT,
                   new Locale("pt","BR")),

                entradas == null ? 0 : entradas,

                saidas == null ? 0 : saidas
            )
        );
    }

    return lista;
}
   public List<Produto> listarAlertasValidade() {

    LocalDate limite =
            LocalDate.now().plusDays(30);

    return produtoRepository
        .buscarAlertasValidadePorPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                limite);
}
 public List<Produto> listarProdutosProximosVencimento() {

    return listarAlertasValidade();

}
}