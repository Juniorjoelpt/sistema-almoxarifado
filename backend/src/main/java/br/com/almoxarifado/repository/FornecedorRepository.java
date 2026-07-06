package br.com.almoxarifado.repository;
import java.util.List;
import br.com.almoxarifado.entity.Fornecedor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository
        extends JpaRepository<Fornecedor, Long> {

    boolean existsByCnpjAndPrefeituraId(
        String cnpj,
        Long prefeituraId);
   
    
    List<Fornecedor> findByPrefeituraIdAndRazaoSocialContainingIgnoreCase( 
        Long prefeituraId,
        String razaoSocial);

    List<Fornecedor> findByPrefeituraIdAndCnpjContaining( 
        Long prefeituraId,
        String cnpj);

    List<Fornecedor> findByPrefeituraIdAndAtivo(
        Long prefeituraId,
        Boolean ativo);
    
    List<Fornecedor> findByPrefeituraIdAndAtivoTrue(
        Long prefeituraId);
    
    Optional<Fornecedor> findByIdAndPrefeituraId(
        Long id,
        Long prefeituraId);
    
    long countByPrefeituraId(Long prefeituraId);
    
    List<Fornecedor> findByPrefeituraId(
        Long prefeituraId);
    
    Page<Fornecedor> findByPrefeituraId(
        Long prefeituraId,
        Pageable pageable);

   Optional<Fornecedor> findByCnpjAndPrefeituraId(
        String cnpj,
        Long prefeituraId);
   
   boolean existsByRazaoSocialAndPrefeituraId(
        String razaoSocial,
        Long prefeituraId);
   
}