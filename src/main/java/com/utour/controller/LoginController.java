package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.ErrorResultDto;
import com.utour.dto.ResultDto;
import com.utour.exception.AuthenticationException;
import com.utour.exception.PasswordIncorrectException;
import com.utour.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController extends CommonController {

    private final LoginService loginService;

    @PostMapping
    public ResultDto<String> login(@RequestParam(value = "loginId") String loginId, @RequestParam(value = "password") String password) {
        return this.ok(Constants.SUCCESS, this.loginService.doLogin(loginId, password));
    }

    @GetMapping
    public ResultDto<Boolean> isExpired(@RequestHeader(value="Authorization") String authorization){
        return this.ok(this.loginService.isExpired(authorization));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResultDto<Void>> exceptionHandler(AuthenticationException authenticationException){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResultDto.<Void>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(authenticationException.getMessage())
                        .build());
    }
}