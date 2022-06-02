package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PagingResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductQueryDto;
import com.utour.dto.product.ProductStoreDto;
import com.utour.dto.product.ProductViewDto;
import com.utour.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    public static final String PRODUCT_IMAGE_LINK_URL = "/v1/product/image/";

    private final ProductService productService;


    @Value(value = "${app.file-upload-storage.product:}")
    private Path productPath;

    @GetMapping(value = "/list")
    public PagingResultDto getPageList (
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String nationCode,
            @RequestParam(required = false) String areaCode,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String productType,
            @RequestHeader(value="Authorization", required = false) String authorization
    ) {
        return this.productService.getPageList(ProductQueryDto.builder()
                .page(Optional.ofNullable(page).orElse(1))
                .query(query)
                .nationCode(nationCode)
                .areaCode(areaCode)
                .productType(Arrays.stream(Constants.ProductType.values()).filter(type -> type.name().equals(productType)).findFirst()
                        .map(Enum::name)
                        .orElse(null))
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(this.useByToken(authorization))
                .build());
    }


    @Authorize
    @GetMapping(value = "/find-all")
    public ResultDto<List<ProductDto>> findAll (@RequestParam String nationCode, @RequestParam(required = false) String areaCode) {
        List<ProductDto> products = this.productService.findAll(ProductDto.builder()
                        .areaCode(areaCode)
                        .nationCode(nationCode)
                .useYn(Constants.Y)
                .build());

        return this.ok(products);
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
    public ResponseEntity<?> getProductImage(@PathVariable Long productId, @PathVariable(required = false) Long productImageGroupId, @PathVariable(required = false) Long productImageId) {
        Path path = (Objects.isNull(productImageGroupId) || Objects.isNull(productImageId)) ?
                this.productService.getImage(productId) : this.productService.getImage(productId, productImageGroupId, productImageId);
        return this.getImageResponseEntity(path);
    }
}
