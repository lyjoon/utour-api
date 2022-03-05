package com.utour.handler;

import com.utour.dto.ErrorResponseDto;
import com.utour.dto.ResponseDto;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object> {

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
}
