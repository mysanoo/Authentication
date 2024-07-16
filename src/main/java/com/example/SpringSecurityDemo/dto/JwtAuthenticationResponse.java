package com.example.SpringSecurityDemo.dto;


import com.example.SpringSecurityDemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthenticationResponse {

    private String token;

    private User user;
}
