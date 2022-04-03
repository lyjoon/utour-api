package com.utour.aspect;

import com.utour.annotation.EncryptValue;
import com.utour.common.CommonComponent;
import com.utour.common.Constants;
import com.utour.util.ErrorUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

//@Aspect
//@Component
public class EncryptAspect extends CommonComponent {


    /**
    TODO : 암호화
    @Before(value = "@annotation(com.utour.annotation.Encryption)")
    public void beforeObject(JoinPoint joinPoint) {
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

    @Before("execution(public * *(.., @com.utour.annotation.EncryptValue (*), ..))")
    public void beforeField(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for(Annotation[] annotations : parameterAnnotations) {
            Object arg = args[atomicInteger.getAndIncrement()];
            for(Annotation annotation : annotations) {
               if(annotation.annotationType().equals(EncryptValue.class)) {
                   if(arg instanceof String) {
                       arg = this.encrypt((String) arg);
                       log.info("enc : {}", arg);
                   }
               }
            }
            //log.info("length : {}", annotations.length);
        }
    }
    */
}
