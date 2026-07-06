package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.AuditoriaMovimentacao;
import br.com.almoxarifado.repository.AuditoriaMovimentacaoRepository;
import org.springframework.stereotype.Service;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.time.LocalDateTime;
import java.util.List;
import br.com.almoxarifado.service.ProdutoService;

@Service
public class AuditoriaMovimentacaoService {

    private final AuditoriaMovimentacaoRepository repository;
    private final PrefeituraContextService prefeituraContextService;

    public AuditoriaMovimentacaoService(
        AuditoriaMovimentacaoRepository repository,
        PrefeituraContextService prefeituraContextService) {

    this.repository = repository;
    this.prefeituraContextService = prefeituraContextService;
}

    public AuditoriaMovimentacao salvar(
            AuditoriaMovimentacao auditoria) {
        
       

        return repository.save(auditoria);
    }

    public List<AuditoriaMovimentacao> listarTodas() {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual());
}

    public List<AuditoriaMovimentacao>
listarPorUsuario(String usuario) {

    return repository.findByUsuarioAndPrefeituraId(
            usuario,
            prefeituraContextService.getPrefeituraIdAtual());
}

    public List<AuditoriaMovimentacao>
listarPorPeriodo(
        LocalDateTime inicio,
        LocalDateTime fim) {

    return repository
            .findByPrefeituraIdAndDataHoraBetween(
                    prefeituraContextService.getPrefeituraIdAtual(),
                    inicio,
                    fim);
}
}