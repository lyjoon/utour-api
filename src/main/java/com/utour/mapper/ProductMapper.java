package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.product.ProductQueryDto;
import com.utour.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends CommonMapper<Product> {

    List<Product> findPage(ProductQueryDto productPagingDto);

    Long count(ProductQueryDto productPagingDto);

}
