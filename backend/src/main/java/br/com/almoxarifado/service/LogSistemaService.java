package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.LogSistema;
import br.com.almoxarifado.repository.LogSistemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogSistemaService {

    private final LogSistemaRepository repository;
    private final PrefeituraContextService prefeituraContextService;

    public LogSistemaService(
            LogSistemaRepository repository,
            PrefeituraContextService prefeituraContextService) {

        this.repository = repository;
         this.prefeituraContextService = prefeituraContextService;
    }

    public void registrar(
            String usuario,
            String acao,
            String entidade,
            String detalhes) {

        LogSistema log = new LogSistema();

        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setDetalhes(detalhes);
        log.setDataHora(LocalDateTime.now());
        
        
        log.setPrefeitura(
        prefeituraContextService.getPrefeituraAtual());

        repository.save(log);
    }

    public List<LogSistema> listar() {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual());
}
}