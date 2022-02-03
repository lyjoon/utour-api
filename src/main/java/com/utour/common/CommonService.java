package com.utour.common;

import com.utour.common.CommonComponent;
import org.springframework.beans.factory.annotation.Value;

public class CommonService extends CommonComponent {


    @Value("${server.servlet.context-path:/api}")
    protected String contextPath;

}
