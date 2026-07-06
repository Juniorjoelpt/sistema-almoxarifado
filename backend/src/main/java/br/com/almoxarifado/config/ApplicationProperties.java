package br.com.almoxarifado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}