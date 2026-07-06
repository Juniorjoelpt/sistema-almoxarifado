package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Produto;
import br.com.almoxarifado.exception.ProdutoNaoEncontradoException;
import br.com.almoxarifado.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import br.com.almoxarifado.service.PrefeituraContextService;

@Service
public class ProdutoService {
    

    private final ProdutoRepository repository;
    private final LogSistemaService logService;
    private final PrefeituraContextService prefeituraContextService;

    public ProdutoService(
        ProdutoRepository repository,
        LogSistemaService logService,
        PrefeituraContextService prefeituraContextService) {

    this.repository = repository;
    this.logService = logService;
    this.prefeituraContextService = prefeituraContextService;
}

    public Produto salvar(Produto produto) {

    produto.setPrefeitura(
            prefeituraContextService.getPrefeituraAtual());

    if (produto.getFatorConversao() == null) {

        produto.setFatorConversao(1.0);

    }

    if (produto.getUnidadeCompra() == null ||
        produto.getUnidadeCompra().isBlank()) {

        produto.setUnidadeCompra(
                produto.getUnidadeEstoque());

    }

    Produto salvo = repository.save(produto);

    return salvo;
}

   public List<Produto> listarTodos() {
    return repository.findByPrefeituraIdAndAtivoTrue(
        prefeituraContextService.getPrefeituraIdAtual()
);
}

   public Produto buscar(Long id) {

    return repository
            .findByIdAndPrefeituraId(
                    id,
                    prefeituraContextService
                            .getPrefeituraIdAtual())
            .orElseThrow(() ->
                    new ProdutoNaoEncontradoException(
                            "Produto não encontrado"));
}

   public void excluir(Long id) {

    Produto produto = buscar(id);

    produto.setAtivo(false);

    repository.save(produto);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "INATIVACAO",
            "PRODUTO",
            produto.getNome());
}
  public List<Produto> listarEstoqueBaixo() {

    return repository.buscarEstoqueBaixoPorPrefeitura(
            prefeituraContextService.getPrefeituraIdAtual());
}
   public Produto atualizar(Long id, Produto produtoAtualizado) {

    Produto produto = buscar(id);

    produto.setCodigo(produtoAtualizado.getCodigo());
    produto.setNome(produtoAtualizado.getNome());
    produto.setCategoria(produtoAtualizado.getCategoria());
    produto.setUnidadeCompra(
        produtoAtualizado.getUnidadeCompra());
    produto.setUnidadeEstoque(
        produtoAtualizado.getUnidadeEstoque());
    produto.setUnidadeEmbalagem(
        produtoAtualizado.getUnidadeEmbalagem());
    produto.setFatorConversao(
        produtoAtualizado.getFatorConversao());
    produto.setEstoqueMinimo(
        produtoAtualizado.getEstoqueMinimo());
    produto.setAtivo(
        produtoAtualizado.getAtivo());

    Produto atualizado = repository.save(produto);

    String usuario =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    logService.registrar(
            usuario,
            "ATUALIZACAO",
            "PRODUTO",
            produto.getNome());

    return atualizado;
}
 public Page<Produto> listarPaginado(
        Pageable pageable) {

    return repository.findByPrefeituraId(
            prefeituraContextService.getPrefeituraIdAtual(),
            pageable);
}
    public List<Produto> filtrarPorNome(
        String nome) {

    return repository
        .findByPrefeituraIdAndNomeContainingIgnoreCaseAndAtivoTrue(
                prefeituraContextService.getPrefeituraIdAtual(),
                nome);
}
public List<Produto> filtrarPorCodigo(
        String codigo) {

    return repository
        .findByPrefeituraIdAndCodigoContainingIgnoreCaseAndAtivoTrue(
                prefeituraContextService.getPrefeituraIdAtual(),
                codigo);
}

public List<Produto> filtrarPorCategoria(
        String categoria) {

    return repository
        .findByPrefeituraIdAndCategoriaNomeContainingIgnoreCase(
                prefeituraContextService.getPrefeituraIdAtual(),
                categoria);
}

public List<Produto> filtrarPorAtivo(
        Boolean ativo) {

    return repository.findByPrefeituraIdAndAtivo(
        prefeituraContextService.getPrefeituraIdAtual(),
        ativo);
}
}
    
