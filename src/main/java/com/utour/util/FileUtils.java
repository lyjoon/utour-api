package com.utour.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class FileUtils {

    public static boolean delete(Path path, String fileName) throws IOException {
        if(!Files.isDirectory(path)) {
            throw new IOException("It's not a directory => '"+ path.toFile().getPath() +"'");
        }
        Path filePath = path.resolve(fileName);
        return delete(filePath);
    }

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
        return uploadFile(toDir, LocalDate.now(), multipartFile);
    }

    /**
     * @param toDir 저장디렉토리
     * @param localDate 날짜폴더
     * @param multipartFile 업로드 파일
     * @return 저장된 파일 full 경로
     * @throws IOException
     */
    public static Path uploadFile(Path toDir, LocalDate localDate, MultipartFile multipartFile) throws IOException {

        String dirPath = toDir.toFile().getPath();
        if(!Files.isDirectory(toDir)) {
            throw new IOException("It's not a directory => '"+ dirPath +"'");
        }

        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = new StringBuilder(localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .append("-")
                .append(UUID.randomUUID())
                .append("." + ext)
                .toString();

        //Path toPath = Paths.get(dirPath, fileName);
        Path toPath = toDir.resolve(fileName);
        multipartFile.transferTo(toPath);
        return toPath;
    }
}
