package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.RequestPagingDto;
import com.utour.dto.hotel.HotelDto;
import com.utour.dto.hotel.HotelFacilityDto;
import com.utour.dto.hotel.HotelImageDto;
import com.utour.dto.hotel.HotelDetailDto;
import com.utour.entity.Hotel;
import com.utour.entity.HotelFacility;
import com.utour.entity.HotelImage;
import com.utour.mapper.HotelFacilityMapper;
import com.utour.mapper.HotelImageMapper;
import com.utour.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService extends CommonService {

    private final HotelMapper hotelMapper;
    private final HotelImageMapper hotelImageMapper;
    private final HotelFacilityMapper hotelFacilityMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(HotelDetailDto hotelDetailDto) {

        Hotel hotel = this.convert(hotelDetailDto.getHotelDto(), Hotel.class);
        this.hotelMapper.save(hotel);

        HotelFacility _hotelFacility = HotelFacility.builder().hotelId(hotel.getHotelId()).build();
        Boolean exists_1 = this.hotelFacilityMapper.exists(_hotelFacility);
        if(exists_1) {
            this.hotelFacilityMapper.delete(_hotelFacility);
        }

        Optional.ofNullable(hotelDetailDto.getHotelFacilities())
                .ifPresent(hotelFacilities -> hotelFacilities.forEach(hotelFacilityDto -> {
                    hotelFacilityDto.setHotelId(hotel.getHotelId());
                    HotelFacility item = this.convert(hotelFacilityDto, HotelFacility.class);

                    this.hotelFacilityMapper.save(item);
                }));

        HotelImage _hotelImage = HotelImage.builder().hotelId(hotel.getHotelId()).build();
        Boolean exists_2 = this.hotelImageMapper.exists(_hotelImage);
        if(exists_2) {
            this.hotelImageMapper.delete(_hotelImage);
        }

        Optional.ofNullable(hotelDetailDto.getHotelImages())
                .ifPresent(hotelImageDtoList -> {
                    for (HotelImageDto hotelImageDto : hotelImageDtoList) {
                        hotelImageDto.setHotelId(hotel.getHotelId());
                        HotelImage hotelImage = this.convert(hotelImageDto, HotelImage.class);

                        this.hotelImageMapper.save(hotelImage);
                    }
                });
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(HotelDto hotelDto) {

        Hotel hotel = this.convert(hotelDto, Hotel.class);

        HotelFacility _hotelFacility = HotelFacility.builder().hotelId(hotel.getHotelId()).build();
        Boolean exists_1 = this.hotelFacilityMapper.exists(_hotelFacility);
        if(exists_1) {
            this.hotelFacilityMapper.delete(_hotelFacility);
        }

        HotelImage _hotelImage = HotelImage.builder().hotelId(hotel.getHotelId()).build();
        Boolean exists_2 = this.hotelImageMapper.exists(_hotelImage);
        if(exists_2) {
            this.hotelImageMapper.delete(_hotelImage);
        }

        this.hotelMapper.delete(hotel);
    }

    public List<HotelDetailDto> paging(RequestPagingDto requestPagingDto) {
        return this.hotelMapper.findPage(requestPagingDto)
                .stream()
                .map(vo -> HotelDetailDto.builder()
                        .hotelDto(this.convert(vo, HotelDto.class))
                        .hotelImages(this.hotelImageMapper.findAll(HotelImage.builder().hotelId(vo.getHotelId()).build())
                                .stream()
                                .map(vo1 -> this.convert(vo1, HotelImageDto.class))
                                .collect(Collectors.toList())
                        )
                        .hotelFacilities(this.hotelFacilityMapper.findAll(HotelFacility.builder().hotelId(vo.getHotelId()).build())
                                .stream()
                                .map(vo2 -> this.convert(vo2, HotelFacilityDto.class))
                                .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());
    }
}
