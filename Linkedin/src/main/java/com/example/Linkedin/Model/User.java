package com.example.Linkedin.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private String field;
    private String workplace;
    private String universityLocation;
    private String dateOfBirth;
    private List<String> specialities;
    private List<String> connectionId;

    public User(String id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return this.id;
    }
}