package br.com.almoxarifado.service;

import br.com.almoxarifado.config.ApplicationProperties;
import br.com.almoxarifado.entity.AuditoriaMovimentacao;
import br.com.almoxarifado.entity.Prefeitura;
import br.com.almoxarifado.entity.Usuario;
import com.lowagie.text.Chunk;
import br.com.almoxarifado.service.PrefeituraContextService;
import br.com.almoxarifado.repository.PrefeituraRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import java.io.FileInputStream;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.poi.ss.util.CellRangeAddress;

@Service
public class AuditoriaExportService {

    private final AuditoriaMovimentacaoService auditoriaService;
    private final PrefeituraContextService prefeituraContextService;
    private final PrefeituraRepository prefeituraRepository;
    private final ApplicationProperties properties;

    public AuditoriaExportService(
        AuditoriaMovimentacaoService auditoriaService,
        PrefeituraContextService prefeituraContextService,
        PrefeituraRepository prefeituraRepository,
          ApplicationProperties properties) {

    this.auditoriaService = auditoriaService;
    this.prefeituraContextService = prefeituraContextService;
    this.prefeituraRepository = prefeituraRepository;
    this.properties = properties;
}

    public byte[] gerarPdf(
        Prefeitura prefeitura,
        Usuario usuario) throws Exception {

        List<AuditoriaMovimentacao> lista =
                auditoriaService.listarTodas();

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        Document document =
                new Document();

        PdfWriter.getInstance(document, out);

        document.open();
        
        Long prefeituraId = prefeituraContextService.getPrefeituraIdAtual();

    if (prefeituraId != null) {

    prefeitura = prefeituraRepository
            .findById(prefeituraId)
            .orElse(null);

}

        // ============================
        // LOGO
        // ============================

        if (prefeitura != null
                && prefeitura.getLogo() != null
                && !prefeitura.getLogo().isBlank()) {

            try {

                String nomeArquivo =
                        prefeitura.getLogo()
                                .replace("/logos/", "");

                Path caminhoLogo = Paths.get(
                    properties.getUploadDir(),
                    "logos",
                    nomeArquivo);

                System.out.println(
                        "LOGO PDF -> "
                                + caminhoLogo.toAbsolutePath());

                Image logo =
                        Image.getInstance(
                                caminhoLogo
                                        .toAbsolutePath()
                                        .toString());

                logo.scaleToFit(90, 90);

                logo.setAlignment(Element.ALIGN_CENTER);

                document.add(logo);

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }

        //=========================================
// FONTES
//=========================================

Font fonteTitulo =
        new Font(
                Font.HELVETICA,
                20,
                Font.BOLD);

Font fonteSubtitulo =
        new Font(
                Font.HELVETICA,
                15,
                Font.BOLD);

Font fonteNormal =
        new Font(
                Font.HELVETICA,
                10);

Font fonteNegrito =
        new Font(
                Font.HELVETICA,
                10,
                Font.BOLD);

        Paragraph prefeituraTitulo =
        new Paragraph(
                prefeitura.getNome(),
                fonteTitulo);

prefeituraTitulo.setAlignment(Element.ALIGN_CENTER);

document.add(prefeituraTitulo);

Paragraph sistema =
        new Paragraph(
                "Sistema de Almoxarifado Inteligente",
                fonteNormal);

sistema.setAlignment(Element.ALIGN_CENTER);

document.add(sistema);

document.add(new Paragraph(" "));

Paragraph relatorio =
        new Paragraph(
                "RELATÓRIO DE AUDITORIA",
                fonteSubtitulo);

relatorio.setAlignment(Element.ALIGN_CENTER);

document.add(relatorio);

document.add(new Paragraph(" "));

Paragraph dados = new Paragraph();

dados.add(
        new Chunk(
                "Prefeitura: ",
                fonteNegrito));

dados.add(
        new Chunk(
                prefeitura.getNome(),
                fonteNormal));

dados.add("\n");

dados.add(
        new Chunk(
                "Emitido por: ",
                fonteNegrito));

dados.add(
        new Chunk(
                usuario.getNome(),
                fonteNormal));

dados.add("\n");

dados.add(
        new Chunk(
                "Data/Hora: ",
                fonteNegrito));

dados.add(
        new Chunk(
                LocalDateTime.now().toString(),
                fonteNormal));

dados.add("\n");

dados.add(
        new Chunk(
                "Registros: ",
                fonteNegrito));

dados.add(
        new Chunk(
                String.valueOf(lista.size()),
                fonteNormal));

document.add(dados);

document.add(new Paragraph(" "));
        // ============================
        // TABELA
        // ============================

        PdfPTable tabela =
                new PdfPTable(6);

        tabela.setWidthPercentage(100);

        tabela.addCell("Usuário");
        tabela.addCell("Produto");
        tabela.addCell("Tipo");
        tabela.addCell("Quantidade");
        tabela.addCell("Data");
        tabela.addCell("Observação");

        for (AuditoriaMovimentacao a : lista) {

            tabela.addCell(
                    String.valueOf(a.getUsuario()));

            tabela.addCell(
                    String.valueOf(a.getProduto()));

            tabela.addCell(
                    String.valueOf(a.getTipo()));

            tabela.addCell(
                    String.valueOf(a.getQuantidade()));

            tabela.addCell(
                    String.valueOf(a.getDataHora()));

            tabela.addCell(
                    a.getObservacao() == null
                            ? ""
                            : a.getObservacao());

        }

        document.add(tabela);

        document.close();

        return out.toByteArray();

    }

    // =============================================
    // EXCEL
    // =============================================

   
public byte[] gerarExcel(
        Prefeitura prefeitura,
        Usuario usuario) throws Exception {

    Long prefeituraId = prefeituraContextService.getPrefeituraIdAtual();

    if (prefeituraId != null) {
        prefeitura = prefeituraRepository
                .findById(prefeituraId)
                .orElse(null);
    }

    List<AuditoriaMovimentacao> lista =
            auditoriaService.listarTodas();

    XSSFWorkbook workbook = new XSSFWorkbook();

    XSSFSheet sheet =
            workbook.createSheet("Relatório de Auditoria");
    
    for (int i = 0; i <= 4; i++) {

    Row r = sheet.createRow(i);

    r.setHeightInPoints(28);

}
    sheet.setColumnWidth(0, 5000);
    sheet.setColumnWidth(1, 5000);
    sheet.setColumnWidth(2, 5000);
    
    //====================================
// LOGO DA PREFEITURA
//====================================

if (prefeitura != null
        && prefeitura.getLogo() != null
        && !prefeitura.getLogo().isBlank()) {

    try {

        String nomeArquivo =
                prefeitura.getLogo()
                        .replace("/logos/", "");

        Path caminhoLogo = Paths.get(
            properties.getUploadDir(),
            "logos",
            nomeArquivo);
        
        System.out.println("=================================");
System.out.println("Logo Banco : " + prefeitura.getLogo());
System.out.println("Logo Excel : " + caminhoLogo.toAbsolutePath());
System.out.println("Existe?    : " + Files.exists(caminhoLogo));
System.out.println("=================================");

        FileInputStream fis =
                new FileInputStream(
                        caminhoLogo.toFile());

        byte[] bytes =
                IOUtils.toByteArray(fis);

        int pictureIdx =
                workbook.addPicture(
                        bytes,
                        Workbook.PICTURE_TYPE_PNG);

        fis.close();

        Drawing<?> drawing =
                sheet.createDrawingPatriarch();

        ClientAnchor anchor =
                workbook.getCreationHelper()
                        .createClientAnchor();

        anchor.setCol1(0);
        anchor.setRow1(0);

        Picture picture =
        drawing.createPicture(anchor, pictureIdx);

        anchor.setCol1(0);
        anchor.setRow1(0);

        anchor.setCol2(2);
        anchor.setRow2(4);

    } catch (Exception ex) {

        ex.printStackTrace();

    }

}

    CreationHelper helper =
            workbook.getCreationHelper();

    //====================================================
    // FONTES
    //====================================================

    org.apache.poi.ss.usermodel.Font tituloFont =
            workbook.createFont();

    tituloFont.setBold(true);
    tituloFont.setFontHeightInPoints((short)18);

    org.apache.poi.ss.usermodel.Font headerFont =
            workbook.createFont();

    headerFont.setBold(true);
    headerFont.setColor(IndexedColors.WHITE.getIndex());

    //====================================================
    // ESTILOS
    //====================================================

    CellStyle tituloStyle =
            workbook.createCellStyle();

    tituloStyle.setFont(tituloFont);
    tituloStyle.setAlignment(HorizontalAlignment.CENTER);
    
    tituloStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    tituloStyle.setFillForegroundColor(
        IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

    tituloStyle.setFillPattern(
        FillPatternType.SOLID_FOREGROUND);

    CellStyle headerStyle =
            workbook.createCellStyle();

    headerStyle.setFont(headerFont);
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    headerStyle.setFillForegroundColor(
        IndexedColors.ROYAL_BLUE.getIndex());

    headerStyle.setFillPattern(
            FillPatternType.SOLID_FOREGROUND);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBorderTop(BorderStyle.THIN);
    headerStyle.setBorderLeft(BorderStyle.THIN);
    headerStyle.setBorderRight(BorderStyle.THIN);

headerStyle.setWrapText(true);

    CellStyle cellStyle =
            workbook.createCellStyle();
    
    cellStyle.setVerticalAlignment(
        VerticalAlignment.CENTER);

    cellStyle.setWrapText(true);

    cellStyle.setBorderBottom(BorderStyle.THIN);
    cellStyle.setBorderTop(BorderStyle.THIN);
    cellStyle.setBorderLeft(BorderStyle.THIN);
    cellStyle.setBorderRight(BorderStyle.THIN);

    CellStyle quantidadeStyle =
            workbook.createCellStyle();

    quantidadeStyle.cloneStyleFrom(cellStyle);
    quantidadeStyle.setAlignment(
            HorizontalAlignment.CENTER);
    
    CellStyle linhaCinzaStyle =
        workbook.createCellStyle();
    
    linhaCinzaStyle.setVerticalAlignment(
        VerticalAlignment.CENTER);

    linhaCinzaStyle.setWrapText(true);

    linhaCinzaStyle.cloneStyleFrom(cellStyle);

    linhaCinzaStyle.setFillForegroundColor(
        IndexedColors.GREY_25_PERCENT.getIndex());

    linhaCinzaStyle.setFillPattern(
        FillPatternType.SOLID_FOREGROUND);
    
    CellStyle quantidadeCinzaStyle =
        workbook.createCellStyle();

    quantidadeCinzaStyle.cloneStyleFrom(linhaCinzaStyle);

    quantidadeCinzaStyle.setAlignment(
        HorizontalAlignment.CENTER);
    
    
    CellStyle dataStyle =
        workbook.createCellStyle();

    dataStyle.cloneStyleFrom(cellStyle);

    dataStyle.setDataFormat(
        helper.createDataFormat()
              .getFormat("dd/MM/yyyy HH:mm"));

    
    CellStyle dataCinzaStyle =
        workbook.createCellStyle();

    dataCinzaStyle.cloneStyleFrom(linhaCinzaStyle);

    dataCinzaStyle.setDataFormat(
        helper.createDataFormat()
                .getFormat("dd/MM/yyyy HH:mm"));

    //====================================================
    // CABEÇALHO
    //====================================================

    int linha = 5;
    int linhaCabecalho = linha;
    
    if (prefeitura != null) {
       
        Row row = sheet.createRow(linha++);

        Cell cell = row.createCell(0);

        cell.setCellValue(prefeitura.getNome());

        cell.setCellStyle(tituloStyle);
        

        sheet.addMergedRegion(
        new CellRangeAddress(
            linha - 1,
            linha - 1,
            0,
            5));
    }
    
    
    Row sistema = sheet.createRow(linha++);
    
    sistema.createCell(0)
            .setCellValue("Sistema de Almoxarifado Inteligente");

    Row emitido = sheet.createRow(linha++);
    emitido.createCell(0)
            .setCellValue("Emitido por:");
    emitido.createCell(1)
            .setCellValue(usuario.getNome());

    Row data = sheet.createRow(linha++);
    data.createCell(0)
            .setCellValue("Data/Hora:");

    data.createCell(1)
            .setCellValue(
                    java.time.format.DateTimeFormatter
                            .ofPattern("dd/MM/yyyy HH:mm")
                            .format(LocalDateTime.now()));

    Row total = sheet.createRow(linha++);
    total.createCell(0)
            .setCellValue("Total de registros:");

    total.createCell(1)
            .setCellValue(lista.size());

    linha++;

    //====================================================
    // CABEÇALHO DA TABELA
    //====================================================

    String[] colunas = {
            "Usuário",
            "Produto",
            "Tipo",
            "Quantidade",
            "Data",
            "Observação"
    };

    Row header = sheet.createRow(linha++);

    linhaCabecalho = header.getRowNum();
    
    for (int i = 0; i < colunas.length; i++) {

        Cell cell = header.createCell(i);

        cell.setCellValue(colunas[i]);

        cell.setCellStyle(headerStyle);

    }
    header.setHeightInPoints(24);

    //====================================================
    // DADOS
    //====================================================

    boolean cinza = false;

    for (AuditoriaMovimentacao a : lista) {

    Row row = sheet.createRow(linha++);
    
    row.setHeightInPoints(20);

    CellStyle estilo =
            cinza ? linhaCinzaStyle : cellStyle;

    CellStyle estiloQuantidade =
            cinza ? quantidadeCinzaStyle : quantidadeStyle;

    CellStyle estiloData =
            cinza ? dataCinzaStyle : dataStyle;

        Cell c0 = row.createCell(0);
        c0.setCellValue(String.valueOf(a.getUsuario()));
        c0.setCellStyle(estilo);

        Cell c1 = row.createCell(1);
        c1.setCellValue(String.valueOf(a.getProduto()));
        c1.setCellStyle(estilo);

        Cell c2 = row.createCell(2);
        c2.setCellValue(a.getTipo().name());
        c2.setCellStyle(estilo);

        Cell c3 = row.createCell(3);
        c3.setCellValue(a.getQuantidade());
        c3.setCellStyle(estiloQuantidade);

        Cell c4 = row.createCell(4);

        if (a.getDataHora() != null) {

            c4.setCellValue(
                    java.sql.Timestamp.valueOf(
                            a.getDataHora()));

            c4.setCellStyle(estiloData);

        }

        Cell c5 = row.createCell(5);

        c5.setCellValue(
                a.getObservacao() == null
                        ? ""
                        : a.getObservacao());

        c5.setCellStyle(estilo);
        
        cinza = !cinza;

    }

    //====================================================
    // AJUSTES
    //====================================================

    for (int i = 0; i < 6; i++) {

    sheet.autoSizeColumn(i);

    sheet.setColumnWidth(
            i,
            Math.min(
                    sheet.getColumnWidth(i) + 1000,
                    15000));

}
    sheet.setColumnWidth(0, 7000);   // Usuário

    sheet.setColumnWidth(1, 9000);   // Produto

    sheet.setColumnWidth(2, 4500);   // Tipo

    sheet.setColumnWidth(3, 3500);   // Quantidade

    sheet.setColumnWidth(4, 5500);   // Data

    sheet.setColumnWidth(5, 12000);  // Observação

    sheet.setAutoFilter(
        new CellRangeAddress(
                linhaCabecalho,
                linha - 1,
                0,
                5));
    sheet.createFreezePane(0, linhaCabecalho + 1);

    ByteArrayOutputStream out =
            new ByteArrayOutputStream();

    workbook.write(out);

    workbook.close();

    return out.toByteArray();

}



}