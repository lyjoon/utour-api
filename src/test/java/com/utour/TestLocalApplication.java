package com.utour;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class TestLocalApplication {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;

    @Test
    public void run() throws Exception {

        Connection connection = dataSource.getConnection();
        log.info("jdbc-url : {}", connection.getMetaData().getURL());
        log.info("user-name : {}", connection.getMetaData().getUserName());
    }

    protected <T>T getBean(Class<T> type) {
        return this.applicationContext.getBean(type);
    }
}
