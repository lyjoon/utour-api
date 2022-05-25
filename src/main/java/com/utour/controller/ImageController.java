package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.ResultDto;
import com.utour.dto.common.ImageDto;
import com.utour.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping(value = "/v1/image")
@RequiredArgsConstructor
public class ImageController extends CommonController {

    @Value(value = "${app.file-upload-storage.temp:}")
    private Path tempPath;

    @Value(value = "${app.file-upload-storage.content:}")
    private Path contentPath;

    @PostMapping(value = "/upload/temp")
    public ResultDto<ImageDto> uploadTemp(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        Path filePath = FileUtils.uploadFile(this.tempPath, multipartFile);
        return this.ok(ImageDto.builder()
                .alt(multipartFile.getOriginalFilename())
                .src(this.contextPath + "/v1/image/temp/" + filePath.toFile().getName())
                .build());
    }


    @GetMapping(value = "/temp/{name:.+}")
    public ResponseEntity<?> getTempImage(@PathVariable(value = "name") String name) throws IOException {
        return this.getImageResponseEntity(this.tempPath.resolve(name));
    }

    @GetMapping(value = "/content/{name:.+}")
    public ResponseEntity<?> getContentImage(@PathVariable(value = "name") String name) throws IOException {
        return this.getImageResponseEntity(this.contentPath.resolve(name));
    }
}
