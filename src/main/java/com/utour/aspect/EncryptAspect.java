package com.utour.aspect;

import com.utour.annotation.EncryptValue;
import com.utour.common.CommonComponent;
import com.utour.common.Constants;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class EncryptAspect extends CommonComponent {

    @Before(value = "@annotation(com.utour.annotation.Encrypt)")
    public void before(JoinPoint joinPoint) {
        for(Object arg : joinPoint.getArgs()) {
            String packageName = arg.getClass().getPackage().getName();
            if(packageName.equals(Constants.ROOT_PACKAGE)) {
                ReflectionUtils.doWithFields(arg.getClass(), field -> {
                    EncryptValue encryptValue = field.getAnnotation(EncryptValue.class);
                    if(Objects.nonNull(encryptValue)) {
                        // get
                        // encrypt
                        // set
                    }
                });
            }
        }
    }

}
