package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.ResultDto;
import com.utour.dto.common.ImageDto;
import com.utour.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/v1/image")
@RequiredArgsConstructor
public class ImageController extends CommonController {

    @Value(value = "${app.file-upload-storage.temp:}")
    private String tempPath;

    @Value(value = "${app.file-upload-storage.content:}")
    private String contentPath;

    @PostMapping(value = "/temp/upload")
    public ResultDto<ImageDto> tempUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        String storeFileName = new StringBuilder(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)).append("_")
                .append(UUID.randomUUID())
                .append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))).toString();

        FileCopyUtils.copy(file.getBytes(), new File(this.tempPath, storeFileName));

        return this.ok(ImageDto.builder()
                .alt(file.getOriginalFilename())
                .src(this.contextPath + "/v1/image/temp/" + storeFileName)
                .build());
    }


    @GetMapping(value = "/temp/{name:.+}")
    public ResponseEntity<?> tempImage(@PathVariable(value = "name") String name) throws IOException {
        return this.getImageResponse(this.tempPath, name);
    }

    @GetMapping(value = "/content/{name:.+}")
    public ResponseEntity<?> contentImage(@PathVariable(value = "name") String name) throws IOException {
        return this.getImageResponse(this.contentPath, name);
    }

    /**
     * @param targetPath
     * @param name
     * @return
     */
    private ResponseEntity<?> getImageResponse(String targetPath, String name) throws IOException {
        // 파일명 저장되었는지 검색(*파일이 존재하지 않은경우 null 을 반환함)
        Path path = Paths.get(targetPath, name);
        if(!Files.isRegularFile(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("not found.");
        }

        // 파일이 검색된 영역임
        // 파일명 확장자에 따른 mediaType 지정
        String fileName = path.toFile().getName();
        String fileExt = FilenameUtils.getExtension(fileName);
        MediaType imageType = MediaType.parseMediaType("image/" + fileExt);

        //매칭되는 확장자가 없을 경우 에러를 반환함.
        if(Objects.isNull(imageType)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("no support file-type");
        }

        try (InputStream inputStream = Files.newInputStream(path)){
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(imageType);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(IOUtils.toByteArray(inputStream));
        } catch (IOException ioException) {
            throw ioException;
        }
    }
}
