package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.hotel.HotelDetailDto;
import com.utour.dto.hotel.HotelDto;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductPagingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/hotel", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HotelController extends CommonController {

    @PutMapping
    public Boolean save(@Valid @RequestBody HotelDetailDto hotelDetailDto) {
        // TODO : 저장
        return true;
    }

    @DeleteMapping(value = "{hotelId}")
    public Boolean delete(@PathVariable Long hotelId) {
        // TODO : 삭제기능
        return true;
    }

    @GetMapping(value = "/list/{page}")
    public List<HotelDto> getList(
            @PathVariable Integer page
    ) {
        // TODO : 목록조회
        return null;
    }
}
