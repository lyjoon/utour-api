package com.utour.aspect;

import com.utour.annotation.EncryptValue;
import com.utour.common.CommonComponent;
import com.utour.common.Constants;
import com.utour.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class EncryptAspect extends CommonComponent {

    @Before(value = "@annotation(com.utour.annotation.Encrypt)")
    public void before(JoinPoint joinPoint) {
        for(Object argument : joinPoint.getArgs()) {
            Class argumentType = argument.getClass();
            String packageName = argumentType.getPackage().getName();
            if(packageName.contains(Constants.ROOT_PACKAGE)) {
                ReflectionUtils.doWithFields(argumentType, field -> {
                    EncryptValue encryptValue = field.getAnnotation(EncryptValue.class);
                    if(Objects.nonNull(encryptValue)) {
                        // get
                        boolean isAccessible = field.isAccessible();
                        // encrypt
                        try {
                            if(!isAccessible) field.setAccessible(true);
                            String value = (String) field.get(argument);
                            // set
                            if(StringUtils.hasText(value)) {
                                String encryptString = this.encrypt(value);
                                field.set(argument, encryptString);
                            }
                        } catch (Throwable throwable) {
                            log.error(ErrorUtils.throwableInfo(throwable));
                        } finally {
                            if(!isAccessible) field.setAccessible(false);
                        }
                    }
                });
            }
        }
    }


    @AfterReturning(value = "@annotation(com.utour.annotation.Decrypt)", returning = "returnValue")
    public void after(JoinPoint joinPoint, Object returnValue) {
        Class returnType = returnValue.getClass();
        String packageName = returnType.getPackage().getName();
        if(packageName.contains(Constants.ROOT_PACKAGE)) {
            ReflectionUtils.doWithFields(returnType, field -> {
                Optional.ofNullable(field.getAnnotation(EncryptValue.class)).ifPresent(encryptValue -> {
                    // get
                    boolean isAccessible = field.isAccessible();
                    // encrypt
                    try {
                        if(!isAccessible) field.setAccessible(true);
                        String value = (String) field.get(returnValue);
                        // set
                        if(StringUtils.hasText(value)) {
                            String decryptString = this.decrypt(value);
                            field.set(returnValue, decryptString);
                        }
                    } catch (Throwable throwable) {
                        log.error(ErrorUtils.throwableInfo(throwable));
                    } finally {
                        if(!isAccessible) field.setAccessible(false);
                    }
                });
            });
        }
    }
}
