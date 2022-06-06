package com.utour.config;

import com.utour.TestLocalApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestConfig extends TestLocalApplication {

    @Autowired
    @Qualifier(value = "jasyptStringEncryptor")
    private StringEncryptor stringEncryptor;

    @Test
    public  void testEncryptString(){
        String id = "roywmc";
        String password = "dldydwns2..";
        log.info("user_id : {} => {}", id, this.stringEncryptor.encrypt(id));
        log.info("password : {} => {}", password, this.stringEncryptor.encrypt(password));
    }


    @Test
    public void testDecrypt() {
        String encString = "8GiJA+vnJQH+MJ5OWuhjCQ==";
        log.info("{} => {}" , encString, stringEncryptor.decrypt(encString));
    }

}
