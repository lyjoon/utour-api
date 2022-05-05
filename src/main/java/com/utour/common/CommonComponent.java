package com.utour.common;

import com.utour.util.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;

public class CommonComponent {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected MessageSourceAccessor messageSourceAccessor;

    @Autowired
    protected StringEncryptor jasyptStringEncryptor;

    protected String getMessage(String code){
        return this.messageSourceAccessor.getMessage(code);
    }

    protected String getMessage(String code, Object[] args){
        return this.messageSourceAccessor.getMessage(code, args);
    }

    protected String getMessage(String code, Object[] args, String defaultMessage){
        return this.messageSourceAccessor.getMessage(code, args, defaultMessage);
    }

    protected String encrypt(String message){
        return jasyptStringEncryptor.encrypt(StringUtils.defaultString(message, ""));
    }

    protected String decrypt(String encryptMessage){
        try {
            return jasyptStringEncryptor.decrypt(encryptMessage);
        } catch (Exception ex) {
            this.log.warn(ex.getMessage());
            return null;
        }
    }

    protected <T> T getBean(Class<T> type) {
        return this.applicationContext.getBean(type);
    }
}
