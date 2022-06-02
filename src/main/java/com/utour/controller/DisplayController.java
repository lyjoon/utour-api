package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.dto.ResultDto;
import com.utour.dto.display.CarouselDto;
import com.utour.dto.display.CommerceDto;
import com.utour.service.DisplayService;
import com.utour.util.StringUtils;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/display" , produces = MediaType.APPLICATION_JSON_VALUE)
public class DisplayController extends CommonController {

    private final DisplayService displayService;

    @GetMapping(value = "/commerce/list")
    public ResultDto<List<CommerceDto>> getCommerceList (
            @RequestHeader(value="Authorization", required = false) String authorization){
        return this.ok(this.displayService.getList(CommerceDto.builder()
                .useYn(this.useByToken(authorization))
                .build()));
    }

    @GetMapping(value = "/carousel/list")
    public ResultDto<List<CarouselDto>> getCarouselList (
            @RequestHeader(value="Authorization", required = false) String authorization){
        return this.ok(this.displayService.getList(CarouselDto.builder()
                .useYn(this.useByToken(authorization))
                .build()));
    }

    @Authorize
    @PutMapping(value = "/commerce")
    public ResultDto<Void> save(
            @Validated(value = ValidatorMarkers.Put.class) @Valid @RequestBody CommerceDto commerceDto) {
        this.displayService.save(commerceDto);
        return this.ok();
    }

    @Authorize
    @PostMapping(value = "/carousel/save", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultDto<Void>> save(@RequestPart(value = "carousel") CarouselDto carouselDto, @RequestPart(required = false, value = "imageFile") MultipartFile multipartFile) throws IOException {
        this.displayService.save(carouselDto, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.ok());
    }

    @Authorize
    @DeleteMapping(value = "/carousel/{carouselId}")
    public ResultDto<Void> deleteCarousel(@PathVariable Long carouselId) throws IOException {
        this.displayService.delete(CarouselDto.builder()
                .carouselId(carouselId)
                .build());
        return this.ok();
    }

    @Authorize
    @DeleteMapping(value = "/commerce/{commerceId}")
    public ResultDto<Void> deleteCommerce(@PathVariable Long commerceId){
        this.displayService.delete(CommerceDto.builder()
                .commerceId(commerceId)
                .build());
        return this.ok();
    }

    @GetMapping(value = "/carousel/{carouselId}")
    public ResponseEntity<?> getCarouselImage(@PathVariable Long carouselId) {
        CarouselDto carouselDto = this.displayService.get(CarouselDto.builder().carouselId(carouselId).build());
        Path path = carouselDto != null && StringUtils.hasText(carouselDto.getImagePath()) ? Paths.get(carouselDto.getImagePath()) : null;
        return this.getImageResponseEntity(path);
    }
}
