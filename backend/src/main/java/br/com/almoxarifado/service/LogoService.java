package br.com.almoxarifado.service;

import br.com.almoxarifado.config.ApplicationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LogoService {

    private final ApplicationProperties properties;

    public LogoService(ApplicationProperties properties) {
        this.properties = properties;
    }

    public String salvar(MultipartFile arquivo) throws IOException {

        Path pasta = Paths.get(
                properties.getUploadDir(),
                "logos"
        );

        Files.createDirectories(pasta);

        String nome =
                java.util.UUID.randomUUID() + ".png";

        System.out.println("PASTA:");
        System.out.println(pasta.toAbsolutePath());

        Files.copy(
                arquivo.getInputStream(),
                pasta.resolve(nome));

        System.out.println("ARQUIVO SALVO:");
        System.out.println(pasta.resolve(nome).toAbsolutePath());

        return "/logos/" + nome;
    }
}