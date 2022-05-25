package com.utour.mapper;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.entity.ViewComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestViewComponentMapper extends TestLocalApplication {

    @Autowired
    private ViewComponentMapper viewComponentMapper;

    @Test
    public void testSave(){
        ViewComponent viewComponent = ViewComponent.builder()
                .title("제목")
                .useYn(Constants.Y)
                .viewComponentType(Constants.ViewComponentType.EDITOR.name())
                .ordinal(1)
                .description("메모")
                .build();
        this.viewComponentMapper.save(viewComponent);

        log.info("{}", viewComponent);
    }

    @Test
    public void testExists(){
        boolean exists = this.viewComponentMapper.exists(ViewComponent.builder().viewComponentId(1L).build());
        log.info("{}", exists);
    }

    @Test
    public void test01(){
        Constants.ViewComponentType viewComponentType = Constants.ViewComponentType.valueOf("BA");
        log.info("{}", viewComponentType);
    }
}
