package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.dto.ApiResponse;
import com.example.SpringSecurityDemo.dto.JwtAuthenticationResponse;
import com.example.SpringSecurityDemo.dto.SignInRequest;
import com.example.SpringSecurityDemo.dto.SignUpRequest;
import com.example.SpringSecurityDemo.entity.User;
import com.example.SpringSecurityDemo.entity.enums.Role;
import com.example.SpringSecurityDemo.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse signUpRequest(SignUpRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(Role.ROLE_USER)
                .build();
        userService.createUser(user);

        String jwt = jwtService.generateToken(user);
        return ApiResponse.builder().message(jwt).data(user).build();
    }

    public ApiResponse signInRequest(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        UserDetails user = userService.userDetailsService().loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        return ApiResponse.builder().data(user).message(jwt).build();
    }


}
