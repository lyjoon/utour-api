package com.utour.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utour.common.CommonComponent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CommonService extends CommonComponent {


    @Value("${server.servlet.context-path:/api}")
    protected String contextPath;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> T convert(Object argument, Class<T> type) {
        return new ModelMapper().map(argument, type);
    }
}
