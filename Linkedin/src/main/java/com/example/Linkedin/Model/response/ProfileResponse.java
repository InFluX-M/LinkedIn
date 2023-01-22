package com.example.Linkedin.Model.response;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class ProfileResponse {
    String name;
    String username;
    String email;
    String field;
    String workplace;
    String universityLocation;
    LocalDate dateOfBirth;
    String profile_url;
    List<String> specialities;
}
