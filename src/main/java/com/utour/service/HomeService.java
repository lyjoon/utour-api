package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.home.HomeCarouselDto;
import com.utour.dto.home.HomePresentDto;
import com.utour.entity.HomeCarousel;
import com.utour.entity.HomePresent;
import com.utour.mapper.HomeCarouselMapper;
import com.utour.mapper.HomePresentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService extends CommonService {

    private final HomePresentMapper homePresentMapper;
    private final HomeCarouselMapper homeCarouselMapper;

    public List<HomePresentDto> getList(HomePresentDto homePresentDto){
        HomePresent homePresent = HomePresent.builder()
                .homePresentId(homePresentDto.getHomePresentId())
                .ordinalPosition(homePresentDto.getOrdinalPosition())
                .productId(homePresentDto.getProductId())
                .useYn(homePresentDto.getUseYn())
                .build();

        return this.homePresentMapper.findAll(homePresent).stream()
                .map(v -> this.convert(v, HomePresentDto.class))
                .collect(Collectors.toList());
    }

    public List<HomeCarouselDto> getList(HomeCarouselDto homeCarouselDto) {
        HomeCarousel homeCarousel = HomeCarousel.builder()
                .imageSrc(homeCarouselDto.getImageSrc())
                .homeCarouselId(homeCarouselDto.getHomeCarouselId())
                .linkUrl(homeCarouselDto.getLinkUrl())
                .ordinalPosition(homeCarouselDto.getOrdinalPosition())
                .useYn(homeCarouselDto.getUseYn())
                .title(homeCarouselDto.getTitle())
                .build();

        return this.homeCarouselMapper.findAll(homeCarousel).stream()
                .map(v -> this.convert(v, HomeCarouselDto.class))
                .collect(Collectors.toList());
    }

    public void save(HomeCarouselDto homeCarouselDto) {
        HomeCarousel homeCarousel = HomeCarousel.builder()
                .imageSrc(homeCarouselDto.getImageSrc())
                .homeCarouselId(homeCarouselDto.getHomeCarouselId())
                .linkUrl(homeCarouselDto.getLinkUrl())
                .ordinalPosition(homeCarouselDto.getOrdinalPosition())
                .useYn(homeCarouselDto.getUseYn())
                .title(homeCarouselDto.getTitle())
                .build();

        this.homeCarouselMapper.save(homeCarousel);
    }

    public void save(HomePresentDto homePresentDto) {
        HomePresent homePresent = HomePresent.builder()
                .homePresentId(homePresentDto.getHomePresentId())
                .ordinalPosition(homePresentDto.getOrdinalPosition())
                .productId(homePresentDto.getProductId())
                .useYn(homePresentDto.getUseYn())
                .build();

        this.homePresentMapper.save(homePresent);
    }

    public void delete(HomeCarouselDto homeCarouselDto) {
        HomeCarousel homeCarousel = HomeCarousel.builder()
                .homeCarouselId(homeCarouselDto.getHomeCarouselId())
                .build();
        this.homeCarouselMapper.delete(homeCarousel);
    }

    public void delete (HomePresentDto homePresentDto) {
        HomePresent homePresent = HomePresent.builder()
                .homePresentId(homePresentDto.getHomePresentId())
                .build();
        this.homePresentMapper.delete(homePresent);
    }
}
