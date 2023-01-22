package com.example.Linkedin.Model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
@AllArgsConstructor
@Builder
public class UserProfile {
    String id;
    String name;
    String username;
    String email;
    String field;
    String workplace;
    String universityLocation;
    LocalDate dateOfBirth;
    String profile_url;
    List<String> specialities;
    Set<String> connectionId;
}
