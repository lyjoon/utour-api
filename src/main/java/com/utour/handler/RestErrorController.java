package com.utour.handler;

import com.utour.dto.ErrorResponseDto;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping({"/error"})
public class RestErrorController implements ErrorController {

    @RequestMapping
    public ErrorResponseDto error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        ErrorResponseDto errorResponseDto;
        if (status == HttpStatus.NO_CONTENT) {
            errorResponseDto = ErrorResponseDto.builder().status(status.value()).message(status.getReasonPhrase()).build();
        } else {
            errorResponseDto = ErrorResponseDto.builder().status(status.value()).message(status.getReasonPhrase()).build();
            Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION))
                    .ifPresent(throwable -> {
                        errorResponseDto.setMessage(throwable.toString());
                    });
        }
        return errorResponseDto;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
