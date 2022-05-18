package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.display.CarouselDto;
import com.utour.dto.display.CommerceDto;
import com.utour.entity.Carousel;
import com.utour.entity.Commerce;
import com.utour.entity.Product;
import com.utour.exception.InternalException;
import com.utour.mapper.CarouselMapper;
import com.utour.mapper.CommerceMapper;
import com.utour.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisplayService extends CommonService {

    private final CommerceMapper commerceMapper;
    private final CarouselMapper carouselMapper;
    private final ProductMapper productMapper;

    public List<CommerceDto> getList(CommerceDto commerceDto){
        Commerce commerce = Commerce.builder()
                .commerceId(commerceDto.getCommerceId())
                .ordinalPosition(commerceDto.getOrdinalPosition())
                .productId(commerceDto.getProductId())
                .useYn(commerceDto.getUseYn())
                .build();

        return this.commerceMapper.findAll(commerce).stream()
                .map(v -> {
                    CommerceDto result = this.convert(v, CommerceDto.class);
                    Product product = productMapper.findById(Product.builder().productId(result.getProductId()).build());
                    if(product != null) {
                        result.setProductType(product.getProductType());
                        result.setTitle(product.getTitle());
                        result.setNationCode(product.getNationCode());
                        result.setAreaCode(product.getAreaCode());
                        result.setRepImageSrc(product.getRepImageSrc());
                        return result;
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public List<CarouselDto> getList(CarouselDto carouselDto) {
        Carousel carousel = Carousel.builder()
                .imageSrc(carouselDto.getImageSrc())
                .carouselId(carouselDto.getCarouselId())
                .linkUrl(carouselDto.getLinkUrl())
                .ordinalPosition(carouselDto.getOrdinalPosition())
                .useYn(carouselDto.getUseYn())
                .title(carouselDto.getTitle())
                .build();

        return this.carouselMapper.findAll(carousel).stream()
                .map(v -> this.convert(v, CarouselDto.class))
                .collect(Collectors.toList());
    }

    public void save(CarouselDto carouselDto) {
        Carousel carousel = Carousel.builder()
                .imageSrc(carouselDto.getImageSrc())
                .carouselId(carouselDto.getCarouselId())
                .linkUrl(carouselDto.getLinkUrl())
                .ordinalPosition(carouselDto.getOrdinalPosition())
                .useYn(carouselDto.getUseYn())
                .title(carouselDto.getTitle())
                .build();

        this.carouselMapper.save(carousel);
    }

    public void save(CommerceDto commerceDto) {
        Commerce commerce = Commerce.builder()
                .commerceId(commerceDto.getCommerceId())
                .ordinalPosition(commerceDto.getOrdinalPosition())
                .productId(commerceDto.getProductId())
                .useYn(commerceDto.getUseYn())
                .build();

        this.commerceMapper.save(commerce);
    }

    @Transactional
    public void delete(CarouselDto carouselDto) {
        Carousel carousel = Carousel.builder()
                .carouselId(carouselDto.getCarouselId())
                .build();
        if(this.carouselMapper.exists(carousel)) {
            this.carouselMapper.delete(carousel);
        } else throw new InternalException(this.getMessage("error.service.data.not-exists"));
    }

    @Transactional
    public void delete (CommerceDto commerceDto) {
        Commerce commerce = Commerce.builder()
                .commerceId(commerceDto.getCommerceId())
                .build();
        if (this.commerceMapper.exists(commerce)) {
            this.commerceMapper.delete(commerce);
        } else throw new InternalException(this.getMessage("error.service.data.not-exists"));
    }
}
