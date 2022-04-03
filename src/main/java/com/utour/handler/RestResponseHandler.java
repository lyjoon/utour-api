package com.utour.handler;

import com.utour.common.CommonComponent;
import com.utour.dto.ErrorResultDto;
import com.utour.exception.ValidException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class RestResponseHandler extends CommonComponent {


    /**
     * valid error 처리
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = ValidException.class)
    public ErrorResultDto validExceptionHandler(ValidException throwable) {
        String validErrorMessage = Optional.ofNullable(throwable.getBindingResult()).map(bindingResult -> {
            FieldError fieldError = bindingResult.getFieldError();
            return Optional.ofNullable(fieldError)
                    .map(f -> new StringBuilder("[" + fieldError.getField() + "]").append(" " + fieldError.getDefaultMessage()).toString())
                    .orElse(messageSourceAccessor.getMessage("error.valid.message"));
        }).orElse(messageSourceAccessor.getMessage("error.valid.message"));

        if(log.isDebugEnabled()) throwable.printStackTrace();

        return ErrorResultDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(validErrorMessage)
                .build();
    }
}
