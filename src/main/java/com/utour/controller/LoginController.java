package com.utour.controller;

import com.utour.common.CommonController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController extends CommonController {

    @PostMapping
    public Boolean login(String loginId, String password) {
        log.info("{}/{}", loginId, password);
        return true;
    }

    @GetMapping
    public Boolean check(){
        return true;
    }
}