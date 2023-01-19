package com.example.Linkedin.Model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@Builder
public class User {
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String field;
    private String workplace;
    private String universityLocation;
    private LocalDate dateOfBirth;
    private List<String> specialities;
    private List<String> connectionId;
    private List<User> requests;

    public User(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}