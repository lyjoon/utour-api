package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.view.*;
import com.utour.entity.*;
import com.utour.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewComponentService extends CommonService {

    private final ViewComponentMapper viewComponentMapper;
    private final ViewComponentTextMapper viewComponentTextMapper;
    private final ViewComponentImageMapper viewComponentImageMapper;
    private final ViewComponentImageSetMapper viewComponentImageSetMapper;
    private final ViewComponentAccommodationMapper viewComponentAccommodationMapper;
    private final ViewComponentFacilityMapper viewComponentFacilityMapper;

    public ViewComponentDto get(ViewComponentDto viewComponentDto) {
        ViewComponent viewComponent = this.viewComponentMapper.findById(ViewComponent.builder()
                .viewComponentId(viewComponentDto.getViewComponentId())
                .viewComponentType(viewComponentDto.getViewComponentType())
                .productId(viewComponentDto.getProductId())
                .useYn(viewComponentDto.getUseYn())
                .build());
        return this.map(viewComponent);
    }

    public List<ViewComponentDto> getList(ViewComponentDto viewComponentDto) {
        List<ViewComponent> viewComponents = this.viewComponentMapper.findAll(ViewComponent.builder()
                .viewComponentId(viewComponentDto.getViewComponentId())
                .viewComponentType(viewComponentDto.getViewComponentType())
                .productId(viewComponentDto.getProductId())
                .useYn(viewComponentDto.getUseYn())
                .build());

        return viewComponents.stream().map(this::map).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private ViewComponentDto map(ViewComponent viewComponent) {
        Constants.VIEW_COMPONENT_TYPE viewComponentType = Arrays.stream(Constants.VIEW_COMPONENT_TYPE.values())
                .filter(type -> type.name().equals(viewComponent.getViewComponentType()))
                .findFirst().orElse(null);

        ViewComponentDto t;

        switch (viewComponentType) {
            case TEXT:
                t = this.getText(viewComponent);
                break;
            case ACCOMMODATION:
                t = this.getAccommodation(viewComponent);
                break;
            case IMAGE:
                t = this.getImage(viewComponent);
                break;
            default:
                t = null;
                break;
        }

        return t;
    }


    private ViewComponentTextDto getText(ViewComponent viewComponent){
        return Optional.ofNullable(this.viewComponentTextMapper.findById(ViewComponentText.builder()
                .viewComponentId(viewComponent.getViewComponentId())
                .build())).map(viewComponentText -> {
            ViewComponentTextDto result = ViewComponentTextDto.builder()
                    .viewComponentId(viewComponent.getViewComponentId())
                    .viewComponentType(viewComponent.getViewComponentType())
                    .productId(viewComponent.getProductId())
                    .title(viewComponent.getTitle())
                    .description(viewComponent.getDescription())
                    .ordinal(viewComponent.getOrdinal())
                    .useYn(viewComponent.getUseYn())
                    .content(viewComponentText.getContent())
                    .build();
            return result;
        }).orElse(null);
    }

    private ViewComponentImageDto getImage(ViewComponent viewComponent) {
        return Optional.ofNullable(this.viewComponentImageMapper.findById(
                        ViewComponentImage.builder()
                                .viewComponentId(viewComponent.getViewComponentId())
                                .build()))
                .map(viewComponentImage -> {
                    ViewComponentImageDto result = ViewComponentImageDto.builder()
                            .viewComponentId(viewComponent.getViewComponentId())
                            .viewComponentType(viewComponent.getViewComponentType())
                            .productId(viewComponent.getProductId())
                            .title(viewComponent.getTitle())
                            .description(viewComponent.getDescription())
                            .ordinal(viewComponent.getOrdinal())
                            .useYn(viewComponent.getUseYn())
                            .displayType(viewComponentImage.getDisplayType())
                            .imagesList(this.viewComponentImageSetMapper.findAll(
                                                    ViewComponentImageSet.builder()
                                                            .viewComponentId(viewComponentImage.getViewComponentId())
                                                            .build()
                                            )
                                            .stream()
                                            .map(viewComponentImageSet -> ViewComponentImageSetDto.builder()
                                                    .viewComponentId(viewComponentImageSet.getViewComponentId())
                                                    .viewComponentSeq(viewComponentImageSet.getViewComponentSeq())
                                                    .imageSrc(viewComponentImageSet.getImageSrc())
                                                    .title(viewComponentImageSet.getTitle())
                                                    .description(viewComponentImageSet.getDescription())
                                                    .build())
                                            .collect(Collectors.toList())
                            )
                            .build();
                    return result;
                }).orElse(null);
    }

    private ViewComponentAccommodationDto getAccommodation(ViewComponent viewComponent) {
        return Optional.ofNullable(this.viewComponentAccommodationMapper.findById(
                        ViewComponentAccommodation.builder()
                                .viewComponentId(viewComponent.getViewComponentId())
                                .build()))
                .map(viewComponentAccommodation -> {
                    ViewComponentAccommodationDto result = ViewComponentAccommodationDto.builder()
                            .viewComponentId(viewComponent.getViewComponentId())
                            .viewComponentType(viewComponent.getViewComponentType())
                            .productId(viewComponent.getProductId())
                            .title(viewComponent.getTitle())
                            .description(viewComponent.getDescription())
                            .ordinal(viewComponent.getOrdinal())
                            .useYn(viewComponent.getUseYn())
                            .fax(viewComponentAccommodation.getFax())
                            .address(viewComponentAccommodation.getAddress())
                            .contact(viewComponentAccommodation.getContact())
                            .url(viewComponentAccommodation.getUrl())
                            .facilities(this.viewComponentFacilityMapper.findAll(
                                            ViewComponentFacility.builder()
                                                    .viewComponentId(viewComponent.getViewComponentId())
                                                    .build()
                                    )
                                    .stream()
                                    .map(viewComponentFacility -> ViewComponentFacilityDto
                                            .builder()
                                            .viewComponentId(viewComponentFacility.getViewComponentId())
                                            .viewComponentSeq(viewComponentFacility.getViewComponentSeq())
                                            .iconType(viewComponentFacility.getIconType())
                                            .title(viewComponentFacility.getTitle())
                                            .description(viewComponentFacility.getDescription())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();

                    return result;
                }).orElse(null);
    }

    public <T extends ViewComponentDto> void save(T t) {

        ViewComponent viewComponent = ViewComponent.builder()
                .viewComponentId(t.getViewComponentId())
                .viewComponentType(t.getViewComponentType())
                .productId(t.getProductId())
                .title(t.getTitle())
                .description(t.getDescription())
                .ordinal(t.getOrdinal())
                .useYn(Optional.ofNullable(t.getUseYn()).orElse(Constants.Y))
                .build();
        this.viewComponentMapper.save(viewComponent);
        t.setViewComponentId(viewComponent.getViewComponentId());

        if(t instanceof ViewComponentAccommodationDto) {
            this.save((ViewComponentAccommodationDto) t);
        } else if(t instanceof ViewComponentTextDto) {
            this.save((ViewComponentTextDto) t);
        } else if(t instanceof ViewComponentImageDto) {
            this.save((ViewComponentImageDto) t);
        }
    }

    private void save(ViewComponentAccommodationDto viewComponentAccommodationDto) {
        ViewComponentAccommodation viewComponentAccommodation = ViewComponentAccommodation.builder()
                .viewComponentId(viewComponentAccommodationDto.getViewComponentId())
                .url(viewComponentAccommodationDto.getUrl())
                .fax(viewComponentAccommodationDto.getFax())
                .address(viewComponentAccommodationDto.getAddress())
                .contact(viewComponentAccommodationDto.getContact())
                .build();
        this.viewComponentAccommodationMapper.save(viewComponentAccommodation);

        Optional.ofNullable(viewComponentAccommodationDto.getFacilities())
                .ifPresent(list -> list.forEach(this::save));
    }

    private void save(ViewComponentTextDto viewComponentTextDto) {
        ViewComponentText viewComponentText = ViewComponentText.builder()
                .viewComponentId(viewComponentTextDto.getViewComponentId())
                .content(viewComponentTextDto.getContent())
                .build();

        this.viewComponentTextMapper.save(viewComponentText);
    }

    private void save(ViewComponentImageDto viewComponentImageDto) {
        ViewComponentImage viewComponentImage = ViewComponentImage.builder()
                .viewComponentId(viewComponentImageDto.getViewComponentId())
                .displayType(viewComponentImageDto.getDisplayType())
                .build();
        this.viewComponentImageMapper.save(viewComponentImage);

        Optional.ofNullable(viewComponentImageDto.getImagesList())
                .ifPresent(imageList -> {
                    imageList.forEach(this::save);
                });
    }

    private void save(ViewComponentImageSetDto viewComponentImageSetDto) {
        ViewComponentImageSet viewComponentImageSet = ViewComponentImageSet.builder()
                .viewComponentId(viewComponentImageSetDto.getViewComponentId())
                .viewComponentSeq(viewComponentImageSetDto.getViewComponentSeq())
                .description(viewComponentImageSetDto.getDescription())
                .title(viewComponentImageSetDto.getTitle())
                .imageSrc(viewComponentImageSetDto.getImageSrc())
                .build();

        this.viewComponentImageSetMapper.save(viewComponentImageSet);
    }

    private void save(ViewComponentFacilityDto viewComponentFacilityDto) {
        ViewComponentFacility viewComponentFacility = ViewComponentFacility.builder()
                .viewComponentSeq(viewComponentFacilityDto.getViewComponentSeq())
                .viewComponentId(viewComponentFacilityDto.getViewComponentId())
                .title(viewComponentFacilityDto.getTitle())
                .description(viewComponentFacilityDto.getDescription())
                .iconType(viewComponentFacilityDto.getIconType())
                .build();
        this.viewComponentFacilityMapper.save(viewComponentFacility);
    }

    public <T extends ViewComponentDto> void delete(T t) {

        if(t instanceof ViewComponentAccommodationDto) {
            this.delete((ViewComponentAccommodationDto) t);
        } else if(t instanceof ViewComponentTextDto) {
            this.delete((ViewComponentTextDto) t);
        } else if(t instanceof ViewComponentImageDto) {
            this.delete((ViewComponentImageDto) t);
        }
        this.viewComponentMapper.delete(ViewComponent.builder()
                .viewComponentId(t.getViewComponentId())
                .viewComponentType(t.getViewComponentType())
                .productId(t.getProductId())
                .build());
    }

    private void delete(ViewComponentTextDto viewComponentTextDto) {
        this.viewComponentTextMapper.delete(ViewComponentText.builder()
                .viewComponentId(viewComponentTextDto.getViewComponentId())
                .build());
    }

    private void delete(ViewComponentAccommodationDto viewComponentAccommodationDto) {
        this.delete(ViewComponentFacilityDto.builder().viewComponentId(viewComponentAccommodationDto.getViewComponentId()).build());
        this.viewComponentAccommodationMapper.delete(ViewComponentAccommodation.builder()
                .viewComponentId(viewComponentAccommodationDto.getViewComponentId())
                .build());
    }

    private void delete(ViewComponentImageDto viewComponentImageDto) {
        this.delete(ViewComponentImageSetDto.builder().viewComponentId(viewComponentImageDto.getViewComponentId()).build());
        this.viewComponentImageMapper.delete(ViewComponentImage.builder()
                .viewComponentId(viewComponentImageDto.getViewComponentId())
                .build());
    }

    private void delete(ViewComponentImageSetDto viewComponentImageSetDto) {
        this.viewComponentImageSetMapper.delete(ViewComponentImageSet.builder()
                .viewComponentId(viewComponentImageSetDto.getViewComponentId())
                .viewComponentSeq(viewComponentImageSetDto.getViewComponentSeq())
                .build());
    }

    private void delete(ViewComponentFacilityDto viewComponentFacilityDto) {
        this.viewComponentFacilityMapper.delete(ViewComponentFacility.builder()
                .viewComponentId(viewComponentFacilityDto.getViewComponentId())
                .viewComponentSeq(viewComponentFacilityDto.getViewComponentSeq())
                .build());
    }

}
