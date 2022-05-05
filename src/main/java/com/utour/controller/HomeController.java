package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.dto.ResultDto;
import com.utour.dto.home.HomeCarouselDto;
import com.utour.dto.home.HomePresentDto;
import com.utour.service.HomeService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/home" , produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController extends CommonController {

    private final HomeService homeService;

    @GetMapping(value = "/present/list")
    public ResultDto<List<HomePresentDto>> getPresentList(@RequestHeader(value="Authorization", required = false) String authorization){
        return this.ok(this.homeService.getList(HomePresentDto.builder()
                .useYn(this.useByToken(authorization))
                .build()));
    }

    @GetMapping(value = "/carousel/list")
    public ResultDto<List<HomeCarouselDto>> getCarouselList(@RequestHeader(value="Authorization", required = false) String authorization){
        return this.ok(this.homeService.getList(HomeCarouselDto.builder()
                .useYn(this.useByToken(authorization))
                .build()));
    }

    @Authorize
    @PutMapping(value = "/present")
    public ResultDto<Void> save(
            @Validated(value = ValidatorMarkers.Put.class) @Valid @RequestBody HomePresentDto homePresentDto) {
        this.homeService.save(homePresentDto);
        return this.ok();
    }

    @Authorize
    @PutMapping(value = "/carousel")
    public ResultDto<Void> save(
            @Validated(value = ValidatorMarkers.Put.class) @Valid @RequestBody HomeCarouselDto homeCarouselDto) {
        this.homeService.save(homeCarouselDto);
        return this.ok();
    }

    @Authorize
    @DeleteMapping(value = "/carousel/{homeId}")
    public ResultDto<Void> deleteCarousel(@PathVariable Long homeId){
        this.homeService.delete(HomeCarouselDto.builder()
                .homeCarouselId(homeId)
                .build());
        return this.ok();
    }

    @Authorize
    @DeleteMapping(value = "/present/{homeId}")
    public ResultDto<Void> deletePresent(@PathVariable Long homeId){
        this.homeService.delete(HomePresentDto.builder()
                .homePresentId(homeId)
                .build());
        return this.ok();
    }

}
