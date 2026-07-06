package br.com.almoxarifado.repository;

import br.com.almoxarifado.entity.Prefeitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrefeituraRepository
        extends JpaRepository<Prefeitura, Long> {

    Optional<Prefeitura>
    findByCnpj(String cnpj);
}