package com.example.Linkedin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private List<User> requests;
    private List<String> specialities;
    private List<String> connectionId;
    @Override
    public String toString() {
        return this.id;
    }
}