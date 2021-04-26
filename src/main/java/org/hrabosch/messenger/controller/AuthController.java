package org.hrabosch.messenger.controller;

import javax.validation.Valid;

import org.hrabosch.messenger.config.properties.JwtTokenProperties;
import org.hrabosch.messenger.model.login.UserLoginRequest;
import org.hrabosch.messenger.model.login.UserLoginResponse;
import org.hrabosch.messenger.model.registration.UserRegistrationRequest;
import org.hrabosch.messenger.model.registration.UserRegistrationResponse;
import org.hrabosch.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private JwtTokenProperties jwtTokenProperties;

    @Autowired
    public AuthController(UserService userService, JwtTokenProperties jwtTokenProperties) {
        this.userService = userService;
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userService.registerUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return userService.loginUser(userLoginRequest);
    }

}
