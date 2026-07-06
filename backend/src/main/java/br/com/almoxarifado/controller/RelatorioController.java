package br.com.almoxarifado.controller;

import br.com.almoxarifado.service.RelatorioService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(
            RelatorioService service) {

        this.service = service;
    }

    @GetMapping("/estoque/pdf")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
    public ResponseEntity<byte[]> estoquePdf()
            throws Exception {

        byte[] pdf =
                service.gerarRelatorioEstoquePDF();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=estoque.pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/estoque/excel")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_PREFEITURA','USUARIO')")
public ResponseEntity<byte[]> estoqueExcel()
        throws Exception {

    byte[] excel =
            service.gerarRelatorioEstoqueExcel();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=estoque.xlsx")
            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)
            .body(excel);
}
}