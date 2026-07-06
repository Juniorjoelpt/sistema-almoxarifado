package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Lote;
import br.com.almoxarifado.repository.LoteRepository;
import org.springframework.stereotype.Service;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LoteService {

    private final LoteRepository repository;
    private final PrefeituraContextService prefeituraContextService;
    private final LogSistemaService logService;

    public LoteService(
        LoteRepository repository,
        PrefeituraContextService prefeituraContextService,LogSistemaService logService) {

    this.repository = repository;
    this.prefeituraContextService = prefeituraContextService;
    this.logService = logService;
}

    public Lote salvar(Lote lote) {

    if (!lote.getProduto()
            .getPrefeitura()
            .getId()
            .equals(prefeituraContextService.getPrefeituraIdAtual())) {

        throw new RuntimeException(
                "Produto não pertence à prefeitura atual");
    }

    Lote salvo = repository.save(lote);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "CADASTRO",
            "LOTE",
            lote.getNumeroLote());

    return salvo;
}

    public List<Lote> listarTodos() {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual());
}
    
   public List<Lote> buscarLotesDisponiveis(
        Long produtoId) {

    return repository.buscarLotesDisponiveis(
            produtoId,
            prefeituraContextService.getPrefeituraIdAtual());
}
   public List<Lote> listarPorProduto(
        Long produtoId) {

    return repository.findByProdutoIdAndPrefeituraId(
            produtoId,
            prefeituraContextService.getPrefeituraIdAtual());
}
}