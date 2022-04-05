package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.view.ViewComponentDto;
import com.utour.entity.ViewComponent;
import com.utour.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewComponentService extends CommonService {

    private final ViewComponentMapper viewComponentMapper;
    private final ViewComponentTextMapper viewComponentTextMapper;
    private final ViewComponentImageMapper viewComponentImageMapper;
    private final ViewComponentImagesMapper viewComponentImagesMapper;
    private final ViewComponentAccommodationMapper viewComponentAccommodationMapper;
    private final ViewComponentFacilityMapper viewComponentFacilityMapper;

    public <T extends ViewComponentDto> void save(T t) {
        //this.viewComponentMapper.save(t);
    }
}
