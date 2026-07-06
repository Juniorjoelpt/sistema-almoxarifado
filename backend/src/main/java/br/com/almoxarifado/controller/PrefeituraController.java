package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.Prefeitura;
import br.com.almoxarifado.service.PrefeituraService;
import java.io.IOException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/prefeituras")
@CrossOrigin("*")
public class PrefeituraController {

    private final PrefeituraService service;

    public PrefeituraController(
            PrefeituraService service) {

        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Prefeitura salvar(
            @RequestBody Prefeitura prefeitura) {

        return service.salvar(prefeitura);
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Prefeitura> listarTodas() {

        return service.listarTodas();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Prefeitura buscar(
            @PathVariable Long id) {

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Prefeitura atualizar(
            @PathVariable Long id,
            @RequestBody Prefeitura prefeitura) {

        return service.atualizar(id, prefeitura);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void excluir(
            @PathVariable Long id) {

        service.excluir(id);
    }
    @PostMapping("/{id}/logo")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Prefeitura uploadLogo(

        @PathVariable Long id,

        @RequestParam("arquivo")
        MultipartFile arquivo)

        throws IOException {

    return service.uploadLogo(id, arquivo);

}
}