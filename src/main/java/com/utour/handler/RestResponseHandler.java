package com.utour.handler;

import com.utour.common.CommonComponent;
import com.utour.dto.ErrorResponseDto;
import com.utour.dto.ResponseDto;
import com.utour.exception.ValidException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class RestResponseHandler extends CommonComponent implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                             MethodParameter returnType,
                             MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                             ServerHttpRequest request,
                             ServerHttpResponse response) {
        if(body instanceof ErrorResponseDto) {
            ErrorResponseDto errorResponseDto = (ErrorResponseDto) body;
            return ResponseEntity.status(errorResponseDto.getStatus()).body(errorResponseDto);
        } else {
            ResponseDto<?> responseDto = ResponseDto.builder()
                    .data(body)
                    .message("success")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        }
    }


    /**
     * valid error 처리
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = ValidException.class)
    public ErrorResponseDto validExceptionHandler(ValidException throwable) {
        String validErrorMessage = Optional.ofNullable(throwable.getBindingResult()).map(bindingResult -> {
            FieldError fieldError = bindingResult.getFieldError();
            return Optional.ofNullable(fieldError)
                    .map(f -> new StringBuilder("[" + fieldError.getField() + "]").append(" " + fieldError.getDefaultMessage()).toString())
                    .orElse(messageSourceAccessor.getMessage("error.valid.message"));
        }).orElse(messageSourceAccessor.getMessage("error.valid.message"));

        if(log.isDebugEnabled()) throwable.printStackTrace();

        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(validErrorMessage)
                .build();
    }
}
