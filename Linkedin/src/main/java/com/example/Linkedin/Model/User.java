package com.example.Linkedin.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
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

    @Override
    public String toString()
    {
        return this.id;
    }
}