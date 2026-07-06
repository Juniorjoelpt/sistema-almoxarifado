package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.AuditoriaMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaMovimentacaoRepository
        extends JpaRepository<AuditoriaMovimentacao, Long> {

    List<AuditoriaMovimentacao>
    findByUsuario(String usuario);

    List<AuditoriaMovimentacao>
    findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim);
    
    List<AuditoriaMovimentacao>
findByPrefeituraId(Long prefeituraId);


List<AuditoriaMovimentacao>
findByUsuarioAndPrefeituraId(
        String usuario,
        Long prefeituraId);




List<AuditoriaMovimentacao>
findByPrefeituraIdAndDataHoraBetween(
        Long prefeituraId,
        LocalDateTime inicio,
        LocalDateTime fim);
    
    
    
}