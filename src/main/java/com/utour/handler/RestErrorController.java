package com.utour.handler;

import com.utour.common.CommonComponent;
import com.utour.dto.ErrorResultDto;
import com.utour.util.ErrorUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.servlet.DispatcherServlet.EXCEPTION_ATTRIBUTE;

@RestController
@RequestMapping({"/error"})
public class RestErrorController extends CommonComponent implements ErrorController {

    @RequestMapping
    public ErrorResultDto error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        ErrorResultDto errorResponseDto = new ErrorResultDto();
        errorResponseDto.setStatus(status.value());
        errorResponseDto.setMessage(getMessage("error.service.internal"));

        Optional.ofNullable(request.getAttribute(EXCEPTION_ATTRIBUTE))
                .map(v -> (Throwable) v)
                .ifPresent(throwable -> {
                    String errorLogMessage = ErrorUtils.throwableInfo(throwable);
                    log.error("{}", errorLogMessage);
                });

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
