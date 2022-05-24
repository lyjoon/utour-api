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
    private final ViewComponentMarkdownEditor viewComponentMarkdownEditor;
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

        return Optional.ofNullable(viewComponents)
                .map(list -> list.stream()
                        .map(this::map)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                ).orElse(null);
    }

    private ViewComponentDto map(ViewComponent viewComponent) {
        Constants.ViewComponentType viewComponentType = Arrays.stream(Constants.ViewComponentType.values())
                .filter(type -> type.name().equals(viewComponent.getViewComponentType()))
                .findFirst().orElse(Constants.ViewComponentType.NA);

        ViewComponentDto t;

        switch (viewComponentType) {
            case MARKDOWN:
                t = this.getText(viewComponent);
                break;
            case ACCOMMODATION:
                t = this.getAccommodation(viewComponent);
                break;
            default:
                t = ViewComponentDto.builder()
                        .productId(viewComponent.getProductId())
                        .viewComponentId(viewComponent.getViewComponentId())
                        .viewComponentType(viewComponentType.name())
                        .description(viewComponent.getDescription())
                        .title(viewComponent.getTitle())
                        .ordinal(viewComponent.getOrdinal())
                        .useYn(viewComponent.getUseYn())
                        .createAt(viewComponent.getCreateAt())
                        .build();
                break;
        }

        return t;
    }


    private ViewComponentEditorDto getText(ViewComponent viewComponent){
        return Optional.ofNullable(this.viewComponentMarkdownEditor.findById(ViewComponentEditor.builder()
                .viewComponentId(viewComponent.getViewComponentId())
                .build())).map(viewComponentEditor -> {
            ViewComponentEditorDto result = ViewComponentEditorDto.builder()
                    .viewComponentId(viewComponent.getViewComponentId())
                    .viewComponentType(viewComponent.getViewComponentType())
                    .productId(viewComponent.getProductId())
                    .title(viewComponent.getTitle())
                    .description(viewComponent.getDescription())
                    .ordinal(viewComponent.getOrdinal())
                    .useYn(viewComponent.getUseYn())
                    .content(viewComponentEditor.getContent())
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
        } else if(t instanceof ViewComponentEditorDto) {
            this.save((ViewComponentEditorDto) t);
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

    private void save(ViewComponentEditorDto viewComponentEditorDto) {
        ViewComponentEditor viewComponentEditor = ViewComponentEditor.builder()
                .viewComponentId(viewComponentEditorDto.getViewComponentId())
                .content(viewComponentEditorDto.getContent())
                .build();

        this.viewComponentMarkdownEditor.save(viewComponentEditor);
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

    /**
     * 요소 전체삭제
     * @param viewComponentDto
     */
    public void deleteAll(ViewComponentDto viewComponentDto) {
        Optional.ofNullable(this.viewComponentMapper.findAll(ViewComponent.builder()
                .viewComponentId(viewComponentDto.getViewComponentId())
                .productId(viewComponentDto.getProductId())
                .build()))
                .ifPresent(list -> {
                    for(ViewComponent viewComponent : list) {
                        if(this.delete(ViewComponentEditorDto.builder().viewComponentId(viewComponent.getViewComponentId()).build())) continue;
                        else if(this.delete(ViewComponentAccommodationDto.builder().viewComponentId(viewComponent.getViewComponentId()).build())) continue;
                    }
                });
    }

    private boolean delete(ViewComponentEditorDto viewComponentEditorDto) {
        ViewComponentEditor viewComponentEditor = ViewComponentEditor.builder()
                .viewComponentId(viewComponentEditorDto.getViewComponentId())
                .build();

        Boolean exists = this.viewComponentMarkdownEditor.exists(viewComponentEditor);
        if(exists) {
            this.viewComponentMarkdownEditor.delete(viewComponentEditor);
            this.delete(ViewComponentDto.builder().viewComponentId(viewComponentEditor.getViewComponentId()).build());
            return true;
        }
        return false;
    }

    private boolean delete(ViewComponentAccommodationDto viewComponentAccommodationDto) {

        ViewComponentAccommodation viewComponentAccommodation = ViewComponentAccommodation.builder()
                .viewComponentId(viewComponentAccommodationDto.getViewComponentId())
                .build();

        if(this.viewComponentAccommodationMapper.exists(viewComponentAccommodation)) {
            this.delete(ViewComponentFacilityDto.builder().viewComponentId(viewComponentAccommodationDto.getViewComponentId()).build());
            this.viewComponentAccommodationMapper.delete(viewComponentAccommodation);
            this.delete(ViewComponentDto.builder().viewComponentId(viewComponentAccommodationDto.getViewComponentId()).build());
            return true;
        }
        return false;
    }


    private void delete(ViewComponentDto viewComponentDto) {
        ViewComponent viewComponent = ViewComponent.builder()
                .viewComponentId(viewComponentDto.getViewComponentId())
                .viewComponentType(viewComponentDto.getViewComponentType())
                .productId(viewComponentDto.getProductId())
                .build();

        if(this.viewComponentMapper.exists(viewComponent)) {
            this.viewComponentMapper.delete(viewComponent);
        }
    }

    private void delete(ViewComponentFacilityDto viewComponentFacilityDto) {
        ViewComponentFacility viewComponentFacility = ViewComponentFacility.builder()
                .viewComponentId(viewComponentFacilityDto.getViewComponentId())
                .viewComponentSeq(viewComponentFacilityDto.getViewComponentSeq())
                .build();

        if(this.viewComponentFacilityMapper.exists(viewComponentFacility)) {
            this.viewComponentFacilityMapper.delete(viewComponentFacility);
        }
    }

}
