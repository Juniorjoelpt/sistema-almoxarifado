package br.com.almoxarifado.controller;

import br.com.almoxarifado.entity.AuditoriaMovimentacao;
import br.com.almoxarifado.entity.Prefeitura;
import br.com.almoxarifado.entity.Usuario;
import br.com.almoxarifado.service.AuditoriaMovimentacaoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.almoxarifado.service.AuditoriaExportService;
import java.time.LocalDateTime;
import java.util.List;
import br.com.almoxarifado.service.UsuarioService;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin("*")
public class AuditoriaMovimentacaoController {

    private final AuditoriaMovimentacaoService service;
    private final AuditoriaExportService exportService;
    private final UsuarioService usuarioService;

    public AuditoriaMovimentacaoController(
            AuditoriaMovimentacaoService service,
            AuditoriaExportService exportService,
            UsuarioService usuarioService) {

        this.service = service;
        this.exportService = exportService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public List<AuditoriaMovimentacao> listarTodas() {

        return service.listarTodas();
    }

    @GetMapping("/usuario/{usuario}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public List<AuditoriaMovimentacao>
    listarPorUsuario(
            @PathVariable String usuario) {

        return service.listarPorUsuario(usuario);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public List<AuditoriaMovimentacao>
    listarPorPeriodo(

            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {

        return service.listarPorPeriodo(
                inicio,
                fim);
    }
    @GetMapping("/pdf")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public ResponseEntity<byte[]> exportarPdf()
        throws Exception {
        
        try {
       Usuario usuario =
        usuarioService.obterUsuarioLogado();

Prefeitura prefeitura =
        usuario.getPrefeitura();

byte[] arquivo =
        exportService.gerarPdf(
                prefeitura,
                usuario);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=auditoria.pdf")
            .contentType(
                    MediaType.APPLICATION_PDF)
            .body(arquivo);
    } catch (Exception e) {

        e.printStackTrace();

        throw new RuntimeException(e);

    }
    
}
    @GetMapping("/excel")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA')")
    public ResponseEntity<byte[]> exportarExcel()
        throws Exception {
        Usuario usuario =
        usuarioService.obterUsuarioLogado();

        Prefeitura prefeitura =
        usuario.getPrefeitura();


byte[] arquivo =
        exportService.gerarExcel(
                prefeitura,
                usuario);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=auditoria.xlsx")
            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(arquivo);
}
}