package com.pandey.jwt.demo.service;

import com.pandey.jwt.demo.config.JwtService;
import com.pandey.jwt.demo.dto.AuthDto;
import com.pandey.jwt.demo.dto.AuthResponseDto;
import com.pandey.jwt.demo.dto.LoginDto;
import com.pandey.jwt.demo.user.Role;
import com.pandey.jwt.demo.user.User;
import com.pandey.jwt.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  JwtService jwtService;
    private  AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(AuthDto authDto) {
        User user = User.builder()
                .email(authDto.email())
                .name(authDto.username())
                .password(passwordEncoder.encode(authDto.password()))
                .role(Role.User)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDto(user.getName(), user.getEmail(), jwtToken);
    }

    @Override
    public AuthResponseDto authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        var user = userRepository
                .findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDto(user.getName(), user.getEmail(), jwtToken);
    }
}
