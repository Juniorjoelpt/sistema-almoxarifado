package br.com.almoxarifado.service;

import br.com.almoxarifado.entity.Produto;
import br.com.almoxarifado.repository.ProdutoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioService {

    private final ProdutoRepository produtoRepository;
    private final PrefeituraContextService prefeituraContextService;

    public RelatorioService(
            ProdutoRepository produtoRepository,
            PrefeituraContextService prefeituraContextService) {

        this.produtoRepository = produtoRepository;
        this.prefeituraContextService = prefeituraContextService;
    }

    public byte[] gerarRelatorioEstoquePDF()
        throws Exception {

    List<Produto> produtos =
            produtoRepository.findByPrefeituraIdAndAtivoTrue(
                    prefeituraContextService.getPrefeituraIdAtual());

    Document document =
            new Document(PageSize.A4);

    ByteArrayOutputStream out =
            new ByteArrayOutputStream();

    PdfWriter.getInstance(document, out);

    document.open();

    document.add(
            new Paragraph("Relatório de Estoque"));

    document.add(new Paragraph(" "));

    PdfPTable tabela =
            new PdfPTable(4);

    tabela.addCell("Código");
    tabela.addCell("Produto");
    tabela.addCell("Estoque");
    tabela.addCell("Mínimo");

    for (Produto produto : produtos) {

        tabela.addCell(produto.getCodigo());
        tabela.addCell(produto.getNome());

        tabela.addCell(
                String.valueOf(
                        produto.getEstoqueAtual()));

        tabela.addCell(
                String.valueOf(
                        produto.getEstoqueMinimo()));
    }

    document.add(tabela);

    document.close();

    return out.toByteArray();
}
    public byte[] gerarRelatorioEstoqueExcel()
        throws Exception {

    List<Produto> produtos =
            produtoRepository.findByPrefeituraIdAndAtivoTrue(
    prefeituraContextService.getPrefeituraIdAtual());

    Workbook workbook =
            new XSSFWorkbook();

    Sheet sheet =
            workbook.createSheet("Estoque");

    Row header =
            sheet.createRow(0);

    header.createCell(0)
            .setCellValue("Código");

    header.createCell(1)
            .setCellValue("Produto");

    header.createCell(2)
            .setCellValue("Estoque Atual");

    header.createCell(3)
            .setCellValue("Estoque Mínimo");

    header.createCell(4)
            .setCellValue("Status");

    int rowNum = 1;

    for (Produto produto : produtos) {

        Row row =
                sheet.createRow(rowNum++);

        row.createCell(0)
                .setCellValue(produto.getCodigo());

        row.createCell(1)
                .setCellValue(produto.getNome());

        row.createCell(2)
                .setCellValue(produto.getEstoqueAtual());

        row.createCell(3)
                .setCellValue(produto.getEstoqueMinimo());

        String status =
                produto.getEstoqueAtual()
                <= produto.getEstoqueMinimo()
                ? "BAIXO"
                : "NORMAL";

        row.createCell(4)
                .setCellValue(status);
    }

    for (int i = 0; i < 5; i++) {
        sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream out =
            new ByteArrayOutputStream();

    workbook.write(out);
    workbook.close();

    return out.toByteArray();
}
}