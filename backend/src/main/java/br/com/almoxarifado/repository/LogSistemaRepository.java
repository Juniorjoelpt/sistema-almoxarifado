package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.LogSistema;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogSistemaRepository
        extends JpaRepository<LogSistema, Long> {
    
    List<LogSistema> findByPrefeituraId(
        Long prefeituraId);
    
}