package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductPagingDto;
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
            @RequestParam(required = false) String displayType,
            @RequestParam(required = false) String productType
    ) {
        return this.productService.getList(ProductPagingDto.builder()
                .page(page)
                .query(query)
                .displayType(displayType)
                .productType(productType)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .useYn(Constants.Y)
                .build());
    }

    @PutMapping
    public Boolean save(@Valid @RequestBody ProductDto productDto) {
        // TODO : 저장
        log.info("save : {}", productDto.toString());
        return true;
    }

    @DeleteMapping(value = "{productId}")
    public Boolean delete(@PathVariable Long productId) {
        // TODO : 삭제
        log.info("delete : {}", productId);
        return true;
    }
}
