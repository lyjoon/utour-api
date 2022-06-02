package com.utour.util;

import com.utour.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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

    public static boolean delete(Path path) {
        try {
            if(Files.exists(path)) {
                Files.delete(path);
                return true;
            } else
                return false;
        } catch (IOException ioException) {
            throw new InternalException(ioException);
        }
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
        String baseDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path toDir2 = toDir.resolve(baseDate);
        if(!Files.isDirectory(toDir2)) {
            Files.createDirectory(toDir2);
        }

        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = new StringBuilder(baseDate)
                .append("$")
                .append(UUID.randomUUID())
                .append("." + ext)
                .toString();

        //Path toPath = Paths.get(dirPath, fileName);
        Path toPath = toDir2.resolve(fileName);
        multipartFile.transferTo(toPath);
        return toPath;
    }

    public static boolean moveTempFile(String fileName, Path tempDir, Path toDir) {
        String baseDate = fileName.indexOf("$") > -1 ? fileName.substring(0, fileName.indexOf("$")) : null;
        Path tempFilePath = Optional.ofNullable(baseDate).map(s -> tempDir.resolve(s).resolve(fileName)).orElse(tempDir.resolve(fileName));

        if(!Files.isRegularFile(tempFilePath)) {
            return false;
        }

        try {
            Path toFilePath;
            if(baseDate != null) {
                Path path = toDir.resolve(baseDate);
                if(!Files.isDirectory(path)) {
                    Files.createDirectory(path);
                }
                toFilePath = path.resolve(fileName);
            } else {
                toFilePath = toDir.resolve(fileName);
            }

            //FileCopyUtils.copy(Files.newInputStream(tempFilePath), Files.newOutputStream(toFilePath));
            Files.move(tempFilePath, toFilePath);

            return true;
        } catch (Exception exception) {
            log.error("{}", ErrorUtils.throwableInfo(exception));
            return false;
        }
    }
}
