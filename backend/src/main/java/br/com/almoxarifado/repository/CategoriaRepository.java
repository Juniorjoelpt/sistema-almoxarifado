package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {
    
    
    List<Categoria> findByPrefeituraId(
        Long prefeituraId);
    
    Optional<Categoria> findByIdAndPrefeituraId(
        Long id,
        Long prefeituraId);
    
    boolean existsByNomeAndPrefeituraId(
        String nome,
        Long prefeituraId);
}