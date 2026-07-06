package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.LogSistema;
import br.com.almoxarifado.service.LogSistemaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin("*")
public class LogSistemaController {

    private final LogSistemaService service;

    public LogSistemaController(
            LogSistemaService service) {

        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<LogSistema> listar() {

        return service.listar();
    }
}