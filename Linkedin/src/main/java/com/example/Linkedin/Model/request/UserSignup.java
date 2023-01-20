package com.example.Linkedin.Model.request;

import lombok.Data;

@Data
public class UserSignup {
    private String username;
    private String email;
    private String password;
}