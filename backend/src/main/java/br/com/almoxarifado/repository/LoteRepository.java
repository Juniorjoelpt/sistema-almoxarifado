package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Lote;
import br.com.almoxarifado.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoteRepository
        extends JpaRepository<Lote, Long> {

    List<Lote> findByProdutoOrderByDataValidadeAsc(
            Produto produto);
    @Query("""
SELECT l
FROM Lote l
WHERE l.produto.id = :produtoId
AND l.produto.prefeitura.id = :prefeituraId
AND l.quantidade > 0
ORDER BY l.dataValidade ASC
""")
List<Lote> buscarLotesDisponiveis(
        @Param("produtoId") Long produtoId,
        @Param("prefeituraId") Long prefeituraId);
    
    
   
    
    @Query("""
SELECT l
FROM Lote l
WHERE l.produto.prefeitura.id = :prefeituraId
ORDER BY l.dataValidade ASC
""")
List<Lote> findByPrefeituraId(
        @Param("prefeituraId") Long prefeituraId);

@Query("""
SELECT l
FROM Lote l
WHERE l.produto.id = :produtoId
AND l.produto.prefeitura.id = :prefeituraId
ORDER BY l.dataValidade ASC
""")
List<Lote> findByProdutoIdAndPrefeituraId(
        @Param("produtoId") Long produtoId,
        @Param("prefeituraId") Long prefeituraId);


Optional<Lote> findByIdAndProdutoPrefeituraId(
        Long id,
        Long prefeituraId);


}