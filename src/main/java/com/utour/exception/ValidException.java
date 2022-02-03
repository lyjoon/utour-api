package com.utour.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * validation 관련 custom 예외 case
 */
@Getter
@AllArgsConstructor
public class ValidException extends RuntimeException {

    private BindingResult bindingResult;

    @Builder
    public ValidException(Throwable throwable, BindingResult bindingResult, String message){
        super(message, throwable);
        this.bindingResult = bindingResult;
    }

}
