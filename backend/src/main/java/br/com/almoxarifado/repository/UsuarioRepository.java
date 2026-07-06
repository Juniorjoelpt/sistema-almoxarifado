package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.enums.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
    
     long countByRoleAndAtivoTrue(Role role);
     
     List<Usuario> findByPrefeituraId(Long prefeituraId);
     
     Page<Usuario> findByPrefeituraId(
        Long prefeituraId,
        Pageable pageable);
     
     
     Optional<Usuario> findByUsernameAndPrefeituraId(
        String username,
        Long prefeituraId);
}