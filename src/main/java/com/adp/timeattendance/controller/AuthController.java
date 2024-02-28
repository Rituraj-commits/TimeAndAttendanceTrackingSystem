package com.adp.timeattendance.controller;

import com.adp.timeattendance.config.UserAuthenticationProvider;
import com.adp.timeattendance.dtos.CredentialsDto;
import com.adp.timeattendance.dtos.SignUptDto;
import com.adp.timeattendance.dtos.UserDto;
import com.adp.timeattendance.service.UserServiceClass;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserServiceClass userServiceClass;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userServiceClass.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUptDto user) {
        UserDto createdUser = userServiceClass.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.ok(createdUser);
    }

}