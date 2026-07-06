package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Categoria;
import br.com.almoxarifado.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import br.com.almoxarifado.service.PrefeituraContextService;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final PrefeituraContextService prefeituraContextService;

    public CategoriaService(
            CategoriaRepository repository,
            PrefeituraContextService prefeituraContextService) {

        this.repository = repository;
        this.prefeituraContextService = prefeituraContextService;
    }

    public Categoria salvar(Categoria categoria) {
        
        categoria.setPrefeitura(
        prefeituraContextService.getPrefeituraAtual()
    );
        
     
        return repository.save(categoria);
    }

    public List<Categoria> listarTodas() {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual());
}

    public Categoria buscar(Long id) {

        return repository
    .findByIdAndPrefeituraId(
        id,
        prefeituraContextService.getPrefeituraIdAtual()
    )
    .orElseThrow(() ->
        new RuntimeException("Categoria não encontrada"));
    }

    public Categoria atualizar(
            Long id,
            Categoria categoria) {

        Categoria existente = buscar(id);

        existente.setNome(
                categoria.getNome());

        return repository.save(existente);
    }

    public void excluir(Long id) {

    Categoria categoria = buscar(id);

    repository.delete(categoria);
}
}