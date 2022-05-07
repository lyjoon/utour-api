package com.utour.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class FileUtils {

    public static boolean delete(Path path) throws IOException {
        if(Files.exists(path)) {
            Files.delete(path);
            return true;
        }
        return false;
    }

    /**
     * @param toDir 저장디렉토리
     * @param multipartFile 업로드 파일
     * @return 저장된 파일 full 경로
     * @throws IOException
     */
    public static Path uploadFile(Path toDir, MultipartFile multipartFile) throws IOException {

        String dirPath = toDir.toFile().getPath();
        if(!Files.isDirectory(toDir)) {
            throw new IOException("It's not a directory => '"+ dirPath +"'");
        }

        String originName = multipartFile.getOriginalFilename();
        String fileName = new StringBuilder(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .append("-")
                .append(UUID.randomUUID())
                .append("-")
                .append(originName)
                .toString();

        Path toPath = Paths.get(dirPath, fileName);
        multipartFile.transferTo(toPath);
        return toPath;
    }
}
