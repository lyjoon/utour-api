package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.RequestPagingDto;
import com.utour.dto.product.ProductDto;
import com.utour.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends CommonController {

    private final ProductService productService;

    @GetMapping(value = "/page/list")
    public List<ProductDto> getPageList(@RequestBody RequestPagingDto requestPagingDto) {
        return this.productService.getPageList(requestPagingDto);
    }
}
