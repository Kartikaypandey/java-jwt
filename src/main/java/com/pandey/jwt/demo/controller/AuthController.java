package com.pandey.jwt.demo.controller;

import com.pandey.jwt.demo.dto.AuthDto;
import com.pandey.jwt.demo.dto.AuthResponseDto;
import com.pandey.jwt.demo.dto.LoginDto;
import com.pandey.jwt.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthDto authDto){
        return new ResponseEntity<>(authService.register(authDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.authenticate(loginDto), HttpStatus.OK);
    }
}
