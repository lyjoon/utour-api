package com.utour.mapper;

import com.utour.TestMapper;
import com.utour.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestHotelMapper extends TestMapper {

    @Autowired
    private HotelMapper hotelMapper;

    @Test
    @Override
    public void exists() {
        boolean exists = this.hotelMapper.exists(Hotel.builder().hotelId(12L).build());
        log.info("exists : {}", exists);
    }

    @Test
    @Override
    public void findById() {
        this.save();
        Hotel hotel = this.hotelMapper.findById(Hotel.builder().hotelId(1L).build());
        log.info("findById : {}", hotel.toString());
    }

    @Override
    public void findAll() {

    }

    @Test
    @Override
    public void save() {
        Hotel hotel = Hotel.builder().hotelId(1L).hotelUrl("www.naver.com").hotelClass("C").writer("홍").hotelName("성도").areaCode("SR").content("asdasd").nationCode("kr").build();
        this.hotelMapper.save(hotel);
        log.info("save:{}", hotel.toString());
    }

    @Test
    @Override
    public void delete() {
        this.hotelMapper.delete(Hotel.builder().hotelId(1L).build());
    }
}
