package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Movimentacao;
import br.com.almoxarifado.entity.Produto;
import br.com.almoxarifado.enums.TipoMovimentacao;
import br.com.almoxarifado.exception.EstoqueInsuficienteException;
import br.com.almoxarifado.repository.MovimentacaoRepository;
import br.com.almoxarifado.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import br.com.almoxarifado.entity.AuditoriaMovimentacao;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.almoxarifado.dto.EntradaProdutoDTO;
import br.com.almoxarifado.entity.Fornecedor;
import br.com.almoxarifado.entity.Lote;
import br.com.almoxarifado.repository.FornecedorRepository;
import br.com.almoxarifado.repository.LoteRepository;
import br.com.almoxarifado.repository.SetorRepository;
import br.com.almoxarifado.entity.Setor;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final AuditoriaMovimentacaoService auditoriaService;
    private final LoteRepository loteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final PrefeituraContextService prefeituraContextService;
    private final SetorRepository setorRepository;
    
    
    public MovimentacaoService(
        MovimentacaoRepository movimentacaoRepository,
        ProdutoRepository produtoRepository,
        AuditoriaMovimentacaoService auditoriaService,
        FornecedorRepository fornecedorRepository,
        LoteRepository loteRepository,
        PrefeituraContextService prefeituraContextService,
        SetorRepository setorRepository) {

    this.movimentacaoRepository = movimentacaoRepository;
    this.produtoRepository = produtoRepository;
    this.auditoriaService = auditoriaService;
    this.fornecedorRepository = fornecedorRepository;
    this.loteRepository = loteRepository;
    this.prefeituraContextService = prefeituraContextService;
    this.setorRepository = setorRepository;
}

    @Transactional
public Movimentacao salvar(Movimentacao movimentacao) {
    
   

    if (movimentacao.getQuantidade() == null
            || movimentacao.getQuantidade() <= 0) {

        throw new RuntimeException(
                "Quantidade deve ser maior que zero");
    }

    Produto produto = produtoRepository.findById(
            movimentacao.getProduto().getId())
            .orElseThrow(() ->
                    new RuntimeException("Produto não encontrado"));
    
                     if (!produto.getPrefeitura().getId().equals(
        prefeituraContextService.getPrefeituraIdAtual())) {

    throw new RuntimeException(
            "Produto não pertence à prefeitura atual");

}
    
            movimentacao.setPrefeitura(
             prefeituraContextService.getPrefeituraAtual()
);

    if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {

    if (movimentacao.getFornecedor() == null) {

        throw new RuntimeException(
                "Fornecedor é obrigatório para entradas.");
    }
    
    

    if (!movimentacao.getFornecedor()
        .getPrefeitura()
        .getId()
        .equals(prefeituraContextService.getPrefeituraIdAtual())) {

    throw new RuntimeException(
            "Fornecedor inválido");
}

    movimentacao.setSetor(null);

    Double quantidadeEntrada = movimentacao.getQuantidade();

if (produto.getFatorConversao() != null
        && produto.getFatorConversao() > 0) {

    quantidadeEntrada =
        movimentacao.getQuantidade()
        * produto.getFatorConversao();
}

produto.setEstoqueAtual(
        produto.getEstoqueAtual()
        + quantidadeEntrada);

    if (movimentacao.getNumeroLote() != null
            && !movimentacao.getNumeroLote().isBlank()) {

        Lote lote = new Lote();

        lote.setNumeroLote(
                movimentacao.getNumeroLote());

        lote.setQuantidade(
                    quantidadeEntrada);

        lote.setDataValidade(
                movimentacao.getDataValidade());

        lote.setProduto(produto);

        loteRepository.save(lote);
    }
}
    


else if (movimentacao.getTipo() == TipoMovimentacao.SAIDA) {

    if (movimentacao.getSetor() == null) {

        throw new RuntimeException(
                "Setor é obrigatório para saídas.");
    }
    Setor setor = setorRepository.findById(
        movimentacao.getSetor().getId())
    .orElseThrow(() ->
        new RuntimeException("Setor não encontrado"));

if (!setor.getPrefeitura().getId()
        .equals(prefeituraContextService.getPrefeituraIdAtual())) {

    throw new RuntimeException("Setor inválido");
}

movimentacao.setSetor(setor);

    movimentacao.setFornecedor(null);

    if (produto.getEstoqueAtual()
            < movimentacao.getQuantidade()) {

        throw new EstoqueInsuficienteException(
                "Estoque insuficiente");
    }

    consumirLotes(
            produto,
            movimentacao.getQuantidade());

    produto.setEstoqueAtual(
            produto.getEstoqueAtual()
                    - movimentacao.getQuantidade());
}

    produtoRepository.save(produto);

    movimentacao.setDataMovimentacao(
            LocalDateTime.now());

    // Salva a movimentação
    Movimentacao movimentacaoSalva =
            movimentacaoRepository.save(movimentacao);

    // Obtém usuário logado
    String usuarioLogado = "SISTEMA";

    if (SecurityContextHolder
            .getContext()
            .getAuthentication() != null) {

        usuarioLogado =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
    }

    // Registra auditoria
    AuditoriaMovimentacao auditoria =
            new AuditoriaMovimentacao();
    
    auditoria.setPrefeitura(
        prefeituraContextService.getPrefeituraAtual());

    auditoria.setUsuario(usuarioLogado);
    auditoria.setProduto(produto.getNome());
    auditoria.setTipo(movimentacao.getTipo());
    auditoria.setQuantidade(movimentacao.getQuantidade());
    auditoria.setDataHora(LocalDateTime.now());
    auditoria.setObservacao(movimentacao.getObservacao());

    auditoriaService.salvar(auditoria);

    return movimentacaoSalva;
}

   public List<Movimentacao> listarTodas() {

    return movimentacaoRepository
            .findByPrefeituraId(
                    prefeituraContextService
                            .getPrefeituraIdAtual());
}
   public Page<Movimentacao> listarPaginado(
        Pageable pageable) {

    return movimentacaoRepository
        .findByPrefeituraId(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                pageable);
}

    public Movimentacao buscar(Long id) {

    return movimentacaoRepository
        .findByIdAndPrefeituraId(
                id,
                prefeituraContextService
                        .getPrefeituraIdAtual())
        .orElseThrow(() ->
                new RuntimeException(
                        "Movimentação não encontrada"));
}

    public void excluir(Long id) {

    Movimentacao mov = buscar(id);

    movimentacaoRepository.delete(mov);
}

    public List<Movimentacao> listarPorProduto(Long produtoId) {
        return movimentacaoRepository
        .findByProdutoIdAndPrefeituraId(
                produtoId,
                prefeituraContextService
                        .getPrefeituraIdAtual());
    }

    public List<Movimentacao> listarPorSetor(Long setorId) {
        return movimentacaoRepository
        .findBySetorIdAndPrefeituraId(
                setorId,
                prefeituraContextService
                        .getPrefeituraIdAtual());
    }
    public List<Movimentacao> listarPorPeriodo(
        LocalDateTime inicio,
        LocalDateTime fim) {

    return movimentacaoRepository
        .listarPorPeriodoPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                inicio,
                fim);
}

public List<Movimentacao> listarEntradasPorPeriodo(
        LocalDateTime inicio,
        LocalDateTime fim) {

    return movimentacaoRepository
        .listarPorTipoPeriodoPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                TipoMovimentacao.ENTRADA,
                inicio,
                fim);
}

public List<Movimentacao> listarSaidasPorPeriodo(
        LocalDateTime inicio,
        LocalDateTime fim) {

    return movimentacaoRepository
        .listarPorTipoPeriodoPrefeitura(
                prefeituraContextService
                        .getPrefeituraIdAtual(),
                TipoMovimentacao.SAIDA,
                inicio,
                fim);
}
public List<Movimentacao> listarPorTipo(
        TipoMovimentacao tipo) {

    return movimentacaoRepository
        .findByTipoAndPrefeituraId(
                tipo,
                prefeituraContextService
                        .getPrefeituraIdAtual());
}
@Transactional
public Movimentacao registrarEntrada(
        EntradaProdutoDTO dto) {

    Produto produto = produtoRepository
            .findById(dto.getProdutoId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Produto não encontrado"));

    Fornecedor fornecedor = fornecedorRepository
            .findById(dto.getFornecedorId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Fornecedor não encontrado"));

    produto.setEstoqueAtual(
            produto.getEstoqueAtual()
                    + dto.getQuantidade());

    produtoRepository.save(produto);

    Lote lote = new Lote();

    lote.setNumeroLote(
            dto.getNumeroLote());

    lote.setQuantidade(
            dto.getQuantidade());

    lote.setDataValidade(
            dto.getDataValidade());

    lote.setProduto(produto);

    loteRepository.save(lote);

    Movimentacao movimentacao =
            new Movimentacao();
    
    movimentacao.setPrefeitura(
        prefeituraContextService.getPrefeituraAtual()
);

    movimentacao.setProduto(produto);

    movimentacao.setFornecedor(
            fornecedor);

    movimentacao.setQuantidade(
            dto.getQuantidade());

    movimentacao.setTipo(
            TipoMovimentacao.ENTRADA);

    movimentacao.setObservacao(
            dto.getObservacao());

    movimentacao.setDataMovimentacao(
            LocalDateTime.now());

    return movimentacaoRepository
            .save(movimentacao);
}
private void consumirLotes(
        Produto produto,
        Double quantidadeSaida) {

    List<Lote> lotes =
            loteRepository.buscarLotesDisponiveis(
        produto.getId(),
        prefeituraContextService.getPrefeituraIdAtual());

    double restante =
            quantidadeSaida;

    for (Lote lote : lotes) {

        if (restante <= 0) {
            break;
        }

        if (lote.getQuantidade()
                >= restante) {

            lote.setQuantidade(
                    lote.getQuantidade()
                            - restante);

            restante = 0;

        } else {

            restante -=
                    lote.getQuantidade();

            lote.setQuantidade(0.0);
        }

        loteRepository.save(lote);
    }

    if (restante > 0) {

        throw new RuntimeException(
                "Quantidade insuficiente nos lotes");
    }
}

}