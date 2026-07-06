package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoAndPrefeituraId(String codigo,Long prefeituraId);

    
    @Query("""
    SELECT p
    FROM Produto p
    WHERE p.prefeitura.id = :prefeituraId
      AND p.ativo = true
      AND p.estoqueAtual IS NOT NULL
      AND p.estoqueMinimo IS NOT NULL
      AND p.estoqueAtual <= p.estoqueMinimo
""")
List<Produto> buscarEstoqueBaixoPorPrefeitura(
        @Param("prefeituraId") Long prefeituraId);

@Query("""
    SELECT p
    FROM Produto p
    WHERE p.prefeitura.id = :prefeituraId
      AND p.dataValidade IS NOT NULL
      AND p.dataValidade <= :dataLimite
    ORDER BY p.dataValidade
""")
List<Produto> buscarAlertasValidadePorPrefeitura(
        @Param("prefeituraId") Long prefeituraId,
        @Param("dataLimite") LocalDate dataLimite);
    
    
    List<Produto> findByPrefeituraIdAndAtivoTrue(
        Long prefeituraId);
    
    
    List<Produto> findByPrefeituraId(Long prefeituraId);

    
    Optional<Produto> findByIdAndPrefeituraId(
        Long id,
        Long prefeituraId);
    
    long countByPrefeituraId(Long prefeituraId);
    
    
    Page<Produto> findByPrefeituraId(
        Long prefeituraId,
        Pageable pageable);
    
    List<Produto> findByPrefeituraIdAndNomeContainingIgnoreCaseAndAtivoTrue(
        Long prefeituraId,
        String nome);
    
    
    List<Produto> findByPrefeituraIdAndCodigoContainingIgnoreCaseAndAtivoTrue(
        Long prefeituraId,
        String codigo);
    
    List<Produto>
    findByPrefeituraIdAndCategoriaNomeContainingIgnoreCase(
        Long prefeituraId,
        String categoria);
    
    List<Produto>
    findByPrefeituraIdAndAtivo(
        Long prefeituraId,
        Boolean ativo);
    
    
    Optional<Produto> findByCodigoAndPrefeituraId(
    String codigo,
    Long prefeituraId
);
    
    
}