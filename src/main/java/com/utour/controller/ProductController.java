package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PagingResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.product.*;
import com.utour.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    private final ProductService productService;

    @Authorize
    @PostMapping(value = "/list")
    public PagingResultDto getList (@RequestBody ProductQueryDto productQueryDto) {

        if(Objects.isNull(productQueryDto.getPage())) {
            productQueryDto.setPage(1);
        }
        if(Objects.isNull(productQueryDto.getLimit())) {
            productQueryDto.setLimit(Constants.DEFAULT_PAGING_COUNT);
        }

        return this.productService.getList(productQueryDto);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<List<ProductAreaResultsDto>> getAreaResults (@RequestParam String arrivalCode, @RequestParam(required = false) String areaCode) {
        List<ProductAreaResultsDto> results = this.productService.getList(arrivalCode, areaCode);
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "{productId}")
    public ResultDto<ProductViewDto> get(@PathVariable Long productId) {
        return this.ok(this.productService.get(productId));
    }

    @Authorize
    @PostMapping(value = "/save", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultDto<Void>> save (
            @RequestPart(value = "product") ProductStoreDto productStoreDto,
            @RequestPart(required = false, value = "repImageFile") MultipartFile multipartFile,
            @RequestPart(required = false, value = "productImageFiles") MultipartFile[] multipartFiles
    ) {
        this.productService.insert(productStoreDto, multipartFile, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.ok());
    }

    @Authorize
    @PostMapping(value = "/update", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultDto<Void>> update (
            @RequestPart(value = "product") ProductStoreDto productStoreDto,
            @RequestPart(required = false, value = "repImageFile") MultipartFile multipartFile,
            @RequestPart(required = false, value = "productImageFiles") MultipartFile[] multipartFiles
    ) throws IOException {
        this.productService.update(productStoreDto, multipartFile, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.ok());
    }



    @Authorize
    @DeleteMapping(value = "{productId}")
    public ResultDto<Void> delete(@PathVariable Long productId) {
        this.productService.delete(productId);
        return this.ok();
    }

    @GetMapping(value = {"/image/{productId}", "/image/{productId}/{productImageGroupId}/{productImageId}"} )
    public ResponseEntity<?> getProductImage(
            @PathVariable Long productId,
            @PathVariable(required = false) Long productImageGroupId,
            @PathVariable(required = false) Long productImageId) {
        Path path = (Objects.isNull(productImageGroupId) || Objects.isNull(productImageId)) ?
                this.productService.getImage(productId) : this.productService.getImage(productId, productImageGroupId, productImageId);
        return this.getImageResponseEntity(path);
    }
}
