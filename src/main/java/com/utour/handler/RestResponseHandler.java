package com.utour.handler;

import com.utour.common.CommonComponent;
import com.utour.dto.ErrorResultDto;
import com.utour.exception.ValidException;
import com.utour.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class RestResponseHandler extends CommonComponent {


    /**
     * valid error 처리
     * @param validException
     * @return
     */
    @ExceptionHandler(value = ValidException.class)
    public ErrorResultDto exceptionHandler(ValidException validException) {
        BindingResult bindingResult = validException.getBindingResult();
        String errorMessage = this.getBindingErrorMessage(bindingResult);
        log.warn("{}", ErrorUtils.throwableInfo(validException));
        return ErrorResultDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResultDto exceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        String errorMessage = this.getBindingErrorMessage(bindingResult);
        log.warn("{}", ErrorUtils.throwableInfo(methodArgumentNotValidException));
        return ErrorResultDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ErrorResultDto exceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        //String errorMessage = httpMessageNotReadableException.getMessage();
        String errorMessage = "Payload(request body) is empty.";
        log.warn("{}", ErrorUtils.throwableInfo(httpMessageNotReadableException));
        return ErrorResultDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();
    }

    private String getBindingErrorMessage(BindingResult bindingResult) {
        return Optional.ofNullable(bindingResult).map(result -> {
            FieldError fieldError = result.getFieldError();
            return Optional.ofNullable(fieldError)
                    .map(f -> new StringBuilder("[" + fieldError.getField() + "]").append(" " + fieldError.getDefaultMessage()).toString())
                    .orElse(messageSourceAccessor.getMessage("error.valid.message"));
        }).orElse(messageSourceAccessor.getMessage("error.valid.message"));
    }

}
