package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.display.CarouselDto;
import com.utour.dto.display.CommerceDto;
import com.utour.entity.Carousel;
import com.utour.entity.Commerce;
import com.utour.entity.Product;
import com.utour.exception.InternalException;
import com.utour.mapper.CarouselMapper;
import com.utour.mapper.CommerceMapper;
import com.utour.mapper.ProductMapper;
import com.utour.util.FileUtils;
import com.utour.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisplayService extends CommonService {

    private final CommerceMapper commerceMapper;
    private final CarouselMapper carouselMapper;
    private final ProductMapper productMapper;

    @Value(value = "${app.file-upload-storage.carousel:}")
    private Path carouselPath;

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
                        result.setArrivalCode(product.getArrivalCode());
                        result.setAreaCode(product.getAreaCode());
                        result.setRepImageSrc(this.contextPath + "/v1/product/image/" + product.getId());
                        return result;
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public List<CarouselDto> getList(CarouselDto carouselDto) {
        Carousel carousel = Carousel.builder()
                .imagePath(carouselDto.getImagePath())
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

    public void save(CarouselDto carouselDto, MultipartFile multipartFile) throws IOException {

        Long carouselId = carouselDto.getCarouselId();
        Carousel.CarouselBuilder carouselBuilder = Carousel.builder()
                .carouselId(carouselId)
                .linkUrl(carouselDto.getLinkUrl())
                .ordinalPosition(carouselDto.getOrdinalPosition())
                .useYn(Optional.ofNullable(carouselDto.getUseYn()).orElse(Constants.Y))
                .title(carouselDto.getTitle());

        // 이미지 파일변경
        if(multipartFile != null && !multipartFile.isEmpty()) {

            if(carouselId != null) {
                Carousel carousel = this.carouselMapper.findById(Carousel.builder().carouselId(carouselId).build());
                if(carousel != null && StringUtils.hasText(carousel.getImagePath())) {
                    FileUtils.delete(Paths.get(carousel.getImagePath()));
                }
            }

            Optional.ofNullable(FileUtils.uploadFile(this.carouselPath, multipartFile))
                    .ifPresent(path -> carouselBuilder.imagePath(path.toFile().getPath()));
        }

        this.carouselMapper.save(carouselBuilder.build());
    }

    public void save(CommerceDto commerceDto) {
        Commerce commerce = Commerce.builder()
                .commerceId(commerceDto.getCommerceId())
                .ordinalPosition(commerceDto.getOrdinalPosition())
                .productId(commerceDto.getProductId())
                .useYn(Optional.ofNullable(commerceDto.getUseYn()).orElse(Constants.Y))
                .build();

        this.commerceMapper.save(commerce);
    }

    @Transactional
    public void delete(CarouselDto carouselDto) throws IOException {
        Carousel carousel = Carousel.builder()
                .carouselId(carouselDto.getCarouselId())
                .build();

        Carousel findCarousel = this.carouselMapper.findById(carousel);
        if(findCarousel != null) {
            if(StringUtils.hasText(findCarousel.getImagePath())) {
                FileUtils.delete(Paths.get(findCarousel.getImagePath()));
            }
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

    public CarouselDto get(CarouselDto carouselDto) {
        return Optional.ofNullable(this.carouselMapper.findById(Carousel.builder().carouselId(carouselDto.getCarouselId()).build()))
                .map(v -> this.convert(v, CarouselDto.class)).orElse(null);
    }
}
