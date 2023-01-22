package com.example.Linkedin.Model.response;

import lombok.Value;

import java.util.List;

@Value
public class UserResponse {
    String id;
    String name;
    String field;
    String workplace;
    String universityLocation;
    List<String> specialities;
}
