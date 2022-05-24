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
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    public static final String PRODUCT_IMAGE_LINK_URL = "/v1/product/image/";

    private final ProductService productService;

    @GetMapping(value = "/list/{page}")
    public PagingResultDto getList(
            @PathVariable Integer page,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String productType
    ) {
        return this.productService.getList(ProductQueryDto.builder()
                .page(page)
                .query(query)
                .productType(Arrays.stream(Constants.ProductType.values()).filter(type -> type.name().equals(productType)).findFirst()
                        .map(Enum::name)
                        .orElse(null))
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Constants.Y)
                .build());
    }

    @Authorize
    @GetMapping(value = "/list-all/{page}")
    public PagingResultDto getList (
            @PathVariable Integer page,
            @RequestParam(required = false) String nationCode,
            @RequestParam(required = false) String areaCode,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Character useYn,
            @RequestParam(required = false) String productType
    ) {
        return this.productService.getList(ProductQueryDto.builder()
                .page(page)
                .query(query)
                .nationCode(nationCode)
                .areaCode(areaCode)
                .productType(Arrays.stream(Constants.ProductType.values()).filter(type -> type.name().equals(productType)).findFirst()
                        .map(Enum::name)
                        .orElse(null))
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Optional.ofNullable(useYn).orElse(Constants.Y))
                .build());
    }


    @Authorize
    @PostMapping(value = "/save", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultDto<Void>> save(
            @RequestPart(value = "product") ProductStoreDto productStoreDto,
            @RequestPart(required = false, value = "repImageFile") MultipartFile multipartFile,
            @RequestPart(required = false, value = "productImageFiles") MultipartFile[] multipartFiles
    ) {
        // this.productService.save(productStoreDto);
        this.log.info("product : {}", productStoreDto.toString());
        this.log.info("rep-image-src : {}", Optional.ofNullable(multipartFile).map(m -> m.getName()).orElse(null));
        this.log.info("product-image-list : {}", Optional.ofNullable(multipartFiles)
                .map(list -> Arrays.stream(list).map(f -> f.getName()).collect(Collectors.joining(",")))
                .orElse(null));

        this.productService.save(productStoreDto, multipartFile, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.ok());
    }

    @Authorize
    @DeleteMapping(value = "{productId}")
    public ResultDto<Void> delete(@PathVariable Long productId) {
        this.productService.delete(ProductDto.builder().productId(productId).build());
        return this.ok();
    }

    @GetMapping(value = "{productId}")
    public ResultDto<ProductViewDto> get(@PathVariable Long productId) {
        return this.ok(this.productService.get(productId));
    }
}
