package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SetorRepository
        extends JpaRepository<Setor, Long> {


    
  

List<Setor> findByPrefeituraIdAndAtivoTrue(
        Long prefeituraId);


Optional<Setor> findByIdAndPrefeituraId(
        Long id,
        Long prefeituraId);

long countByPrefeituraId(Long prefeituraId);

Page<Setor> findByPrefeituraId(
        Long prefeituraId,
        Pageable pageable);



List<Setor>
findByPrefeituraIdAndNomeContainingIgnoreCase(
        Long prefeituraId,
        String nome);

List<Setor>
findByPrefeituraIdAndResponsavelContainingIgnoreCase(
        Long prefeituraId,
        String responsavel);

List<Setor>
findByPrefeituraIdAndAtivo(
        Long prefeituraId,
        Boolean ativo);

boolean existsByNomeAndPrefeituraId(
        String nome,
        Long prefeituraId);

}