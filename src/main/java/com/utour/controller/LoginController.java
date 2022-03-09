package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController extends CommonController {

    private final LoginService loginService;

    @PostMapping
    public String login(@RequestParam(value = "loginId") String loginId, @RequestParam(value = "password") String password) {
        return this.loginService.doLogin(loginId, password);
    }

    @GetMapping
    public Boolean check(String token){
        return this.loginService.expired(token);
    }
}