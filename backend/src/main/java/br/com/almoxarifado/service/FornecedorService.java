package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Fornecedor;
import br.com.almoxarifado.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;
    private final PrefeituraContextService prefeituraContextService;
    private final LogSistemaService logService;

    public FornecedorService(FornecedorRepository repository,
            PrefeituraContextService prefeituraContextService,
             LogSistemaService logService) {
        
        
        this.repository = repository;
        this.prefeituraContextService = prefeituraContextService;
        this.logService = logService;
    }

    public Fornecedor salvar(Fornecedor fornecedor) {

    fornecedor.setPrefeitura(
            prefeituraContextService.getPrefeituraAtual()
    );
    if (repository.existsByCnpjAndPrefeituraId(
        fornecedor.getCnpj(),
        prefeituraContextService.getPrefeituraIdAtual())) {

    throw new RuntimeException(
            "Já existe fornecedor com este CNPJ");
}
    if (fornecedor.getRazaoSocial() == null
        || fornecedor.getRazaoSocial().isBlank()) {

    throw new RuntimeException(
            "Razão social obrigatória");
}

if (fornecedor.getCnpj() == null
        || fornecedor.getCnpj().isBlank()) {

    throw new RuntimeException(
            "CNPJ obrigatório");
}

    Fornecedor salvo = repository.save(fornecedor);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "CADASTRO",
            "FORNECEDOR",
            fornecedor.getRazaoSocial());
    
    
    

    return salvo;
}

   public List<Fornecedor> listarTodos() {

    return repository.findByPrefeituraIdAndAtivoTrue(
        prefeituraContextService.getPrefeituraIdAtual()
    );
}

   public Fornecedor buscar(Long id) {

    return repository
            .findByIdAndPrefeituraId(
                    id,
                    prefeituraContextService
                            .getPrefeituraIdAtual())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Fornecedor não encontrado"));
}

    public void excluir(Long id) {

    Fornecedor fornecedor = buscar(id);

    fornecedor.setAtivo(false);

    repository.save(fornecedor);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "INATIVACAO",
            "FORNECEDOR",
            fornecedor.getRazaoSocial());
}
    public Fornecedor atualizar(Long id, Fornecedor atualizado) {

    Fornecedor fornecedor = buscar(id);

    fornecedor.setRazaoSocial(atualizado.getRazaoSocial());
    fornecedor.setNomeFantasia(atualizado.getNomeFantasia());
    fornecedor.setCnpj(atualizado.getCnpj());
    fornecedor.setTelefone(atualizado.getTelefone());
    fornecedor.setEmail(atualizado.getEmail());
    fornecedor.setEndereco(atualizado.getEndereco());
    fornecedor.setAtivo(atualizado.getAtivo());
    
    Optional<Fornecedor> existente =
        repository.findByCnpjAndPrefeituraId(
                atualizado.getCnpj(),
                prefeituraContextService.getPrefeituraIdAtual());

if (existente.isPresent()
        && !existente.get().getId().equals(id)) {

    throw new RuntimeException(
            "Já existe fornecedor com este CNPJ");
}

if (fornecedor.getRazaoSocial() == null
        || fornecedor.getRazaoSocial().isBlank()) {

    throw new RuntimeException(
            "Razão social obrigatória");
}

if (fornecedor.getCnpj() == null
        || fornecedor.getCnpj().isBlank()) {

    throw new RuntimeException(
            "CNPJ obrigatório");
}
    
    

    Fornecedor salvo = repository.save(fornecedor);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "ATUALIZACAO",
            "FORNECEDOR",
            fornecedor.getRazaoSocial());

    return salvo;
}
    public Page<Fornecedor> listarPaginado(
        Pageable pageable) {

    return repository.findByPrefeituraId(
        prefeituraContextService.getPrefeituraIdAtual(),
        pageable);
    }
    public List<Fornecedor> filtrarPorRazaoSocial(
        String razaoSocial) {

    return repository
            .findByPrefeituraIdAndRazaoSocialContainingIgnoreCase(
                    prefeituraContextService.getPrefeituraIdAtual(),
                    razaoSocial);
}

public List<Fornecedor> filtrarPorCnpj(
        String cnpj) {

    return repository
            .findByPrefeituraIdAndCnpjContaining(
                    prefeituraContextService.getPrefeituraIdAtual(),
                    cnpj);
}

public List<Fornecedor> filtrarPorAtivo(
        Boolean ativo) {

    return repository
            .findByPrefeituraIdAndAtivo(
                    prefeituraContextService.getPrefeituraIdAtual(),
                    ativo);
}
}