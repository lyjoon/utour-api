package com.utour.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptValue {
    int originLength() default 0;
    int length() default 0;
}
