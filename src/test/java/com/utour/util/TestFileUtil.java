package com.utour.util;

import com.utour.TestLocalApplication;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestFileUtil extends TestLocalApplication {

    @Test
    public void testEncName() throws UnsupportedEncodingException {
        String fileName = "product_4$" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        log.info("fileName : {} -> {}", fileName,
                URLEncoder.encode(fileName, "UTF-8"));

    }
}
