package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Movimentacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.almoxarifado.enums.TipoMovimentacao;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import br.com.almoxarifado.dto.ProdutoMovimentacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;




public interface MovimentacaoRepository
        extends JpaRepository<Movimentacao, Long> {
    
   

@Query("""
    SELECT COALESCE(SUM(m.quantidade),0)
    FROM Movimentacao m
    WHERE m.prefeitura.id = :prefeituraId
      AND m.tipo = :tipo
      AND YEAR(m.dataMovimentacao) = :ano
      AND MONTH(m.dataMovimentacao) = :mes
""")
Double totalMovimentadoMesPorPrefeitura(
        @Param("prefeituraId") Long prefeituraId,
        @Param("tipo") TipoMovimentacao tipo,
        @Param("ano") int ano,
        @Param("mes") int mes
);


@Query("""
SELECT new br.com.almoxarifado.dto.ProdutoMovimentacaoDTO(
    m.produto.nome,
    COUNT(m))
FROM Movimentacao m
WHERE m.prefeitura.id = :prefeituraId
GROUP BY m.produto.nome
ORDER BY COUNT(m) DESC
""")
List<ProdutoMovimentacaoDTO>
listarProdutosMaisMovimentadosPorPrefeitura(
        @Param("prefeituraId")
        Long prefeituraId);



@Query("""
SELECT m
FROM Movimentacao m
WHERE m.prefeitura.id = :prefeituraId
AND m.dataMovimentacao
BETWEEN :inicio AND :fim
""")
List<Movimentacao> listarPorPeriodoPrefeitura(
        @Param("prefeituraId") Long prefeituraId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);


@Query("""
SELECT m
FROM Movimentacao m
WHERE m.prefeitura.id = :prefeituraId
AND m.tipo = :tipo
AND m.dataMovimentacao
BETWEEN :inicio AND :fim
""")
List<Movimentacao> listarPorTipoPeriodoPrefeitura(
        @Param("prefeituraId") Long prefeituraId,
        @Param("tipo") TipoMovimentacao tipo,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);

   

   
    
  
    
    List<Movimentacao> findByPrefeituraId(
        Long prefeituraId);

Page<Movimentacao> findByPrefeituraId(
        Long prefeituraId,
        Pageable pageable);

Optional<Movimentacao> findByIdAndPrefeituraId(
        Long id,
        Long prefeituraId);
    

long countByPrefeituraId(Long prefeituraId);

List<Movimentacao>
findTop10ByPrefeituraIdOrderByDataMovimentacaoDesc(
        Long prefeituraId);
    

long countByPrefeituraIdAndDataMovimentacaoBetween(
        Long prefeituraId,
        LocalDateTime inicio,
        LocalDateTime fim);


List<Movimentacao> findByProdutoIdAndPrefeituraId(
        Long produtoId,
        Long prefeituraId);


List<Movimentacao> findBySetorIdAndPrefeituraId(
        Long setorId,
        Long prefeituraId);


List<Movimentacao> findByTipoAndPrefeituraId(
        TipoMovimentacao tipo,
        Long prefeituraId);


}
