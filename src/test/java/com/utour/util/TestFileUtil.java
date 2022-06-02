package com.utour.util;

import com.utour.TestLocalApplication;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestFileUtil extends TestLocalApplication {

    @Test
    public void testEncName() throws UnsupportedEncodingException {
        String fileName = "product_4$" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        log.info("fileName : {} -> {}", fileName,
                URLEncoder.encode(fileName, "UTF-8"));

    }
    @Test
    public void testClassPathFile() throws UnsupportedEncodingException {
        ClassPathResource classPathResource = new ClassPathResource("/static/assets/images/no_image.jpg");
        Path path = Paths.get(classPathResource.getPath());
        log.info("file-path : {}", path.toFile().getAbsolutePath());

    }
}
