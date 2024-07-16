package com.example.SpringSecurityDemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String email;

    private String password;

}
