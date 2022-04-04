package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.ResultDto;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductQueryDto;
import com.utour.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    private final ProductService productService;

    @GetMapping(value = "/list/{page}")
    public List<ProductDto> getList(
            @PathVariable Integer page,
            @RequestParam(required = false) String query,
            @RequestParam(required = false, name = "display_type") String displayType,
            @RequestParam(required = false, name = "product_type") String productType
    ) {
        return this.productService.getList(ProductQueryDto.builder()
                .page(page)
                .query(query)
                .productType(productType)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Constants.Y)
                .build());
    }

    @Authorize
    @PutMapping(value = "/create")
    public ResultDto<Void> create(@Valid @RequestBody ProductDto productDto) {
        log.info("save : {}", productDto.toString());
        this.productService.create(productDto);
        return this.ok();
    }

    @Authorize
    @DeleteMapping(value = "{productId}")
    public Boolean delete(@PathVariable Long productId) {
        // TODO : 삭제
        log.info("delete : {}", productId);
        return true;
    }
}
