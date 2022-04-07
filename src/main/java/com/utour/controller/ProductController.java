package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductQueryDto;
import com.utour.dto.product.ProductViewDto;
import com.utour.service.ProductService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    private final ProductService productService;

    @GetMapping(value = "/list/{page}")
    public PaginationResultDto getList(
            @PathVariable Integer page,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String productType
    ) {
        return this.productService.getList(ProductQueryDto.builder()
                .page(page)
                .query(query)
                .productType(Arrays.stream(Constants.PRODUCT_TYPE.values()).filter(type -> type.name().equals(productType)).findFirst()
                        .map(Enum::name)
                        .orElse(null))
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Constants.Y)
                .build());
    }

    @Authorize
    @GetMapping(value = "/list-all/{page}")
    public PaginationResultDto getList (
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
                .productType(Arrays.stream(Constants.PRODUCT_TYPE.values()).filter(type -> type.name().equals(productType)).findFirst()
                        .map(Enum::name)
                        .orElse(null))
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Optional.ofNullable(useYn).orElse(Constants.Y))
                .build());
    }

    @Authorize
    @PutMapping(value = "/create")
    public ResultDto<Void> create(@Validated(ValidatorMarkers.Put.class) @Valid @RequestBody ProductDto productDto) {
        this.productService.create(productDto);
        return this.ok();
    }

    @Authorize
    @PutMapping
    public ResultDto<Void> save(@Valid @Validated(value = ValidatorMarkers.Put.class) @RequestBody ProductViewDto productViewDto) {
        this.productService.save(productViewDto);
        return this.ok();
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
