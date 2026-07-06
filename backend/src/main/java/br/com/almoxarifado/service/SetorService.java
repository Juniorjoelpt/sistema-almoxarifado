package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Setor;
import br.com.almoxarifado.repository.SetorRepository;
import org.springframework.stereotype.Service;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
public class SetorService {
   
    private final SetorRepository repository;
    private final PrefeituraContextService prefeituraContextService;
    private final LogSistemaService logService;

    public SetorService(
        SetorRepository repository,
        PrefeituraContextService prefeituraContextService,
        LogSistemaService logService) {

    this.repository = repository;
    this.prefeituraContextService = prefeituraContextService;
    this.logService = logService;
}

    public Setor salvar(Setor setor) {
        
        setor.setPrefeitura(
            prefeituraContextService.getPrefeituraAtual()
    );
      
        Setor salvo = repository.save(setor);

String usuario =
        SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

logService.registrar(
        usuario,
        "CADASTRO",
        "SETOR",
        setor.getNome());

return salvo;
        
    }
    
    

    public List<Setor> listarTodos() {
    return repository.findByPrefeituraIdAndAtivoTrue(
        prefeituraContextService.getPrefeituraIdAtual()
);
    }
    public Page<Setor> listarPaginado(
        Pageable pageable) {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual(),
            pageable);
}

    public Setor buscar(Long id) {

    return repository
            .findByIdAndPrefeituraId(
                    id,
                    prefeituraContextService
                            .getPrefeituraIdAtual())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Setor não encontrado"));
}

    public void excluir(Long id) {

    Setor setor = buscar(id);

    setor.setAtivo(false);

    repository.save(setor);
    String usuario =
        SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

logService.registrar(
        usuario,
        "INATIVACAO",
        "SETOR",
        setor.getNome());
}
    public Setor atualizar(Long id, Setor atualizado) {

    Setor setor = buscar(id);

    setor.setNome(atualizado.getNome());
    setor.setResponsavel(atualizado.getResponsavel());
    setor.setTelefone(atualizado.getTelefone());
    setor.setAtivo(atualizado.getAtivo());

    Setor atualizadoSetor = repository.save(setor);

String usuario =
        SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

logService.registrar(
        usuario,
        "ATUALIZACAO",
        "SETOR",
        setor.getNome());

return atualizadoSetor;
}   
    public List<Setor> filtrarPorNome(
        String nome) {

    return repository
        .findByPrefeituraIdAndNomeContainingIgnoreCase(
                prefeituraContextService.getPrefeituraIdAtual(),
                nome);
}

public List<Setor> filtrarPorResponsavel(
        String responsavel) {

    return repository
        .findByPrefeituraIdAndResponsavelContainingIgnoreCase(
                prefeituraContextService.getPrefeituraIdAtual(),
                responsavel);
}

public List<Setor> filtrarPorAtivo(
        Boolean ativo) {

    return repository.findByPrefeituraIdAndAtivo(
        prefeituraContextService.getPrefeituraIdAtual(),
        ativo);
}



}