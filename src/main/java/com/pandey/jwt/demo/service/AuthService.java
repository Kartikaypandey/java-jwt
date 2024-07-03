package com.pandey.jwt.demo.service;

import com.pandey.jwt.demo.dto.AuthDto;
import com.pandey.jwt.demo.dto.AuthResponseDto;
import com.pandey.jwt.demo.dto.LoginDto;

public interface AuthService {
    AuthResponseDto register(AuthDto authDto);
    AuthResponseDto authenticate(LoginDto loginDto);

}
