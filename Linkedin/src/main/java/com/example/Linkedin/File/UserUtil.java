package com.example.Linkedin.File;

import lombok.Data;

import java.util.List;

@Data
public class UserUtil {
    private String id;
    private String name;
    private String profile_pic;
    private String email;
    private String dateOfBirth;
    private String universityLocation;
    private String field;
    private String workplace;
    private List<String> specialties;
    private List<String> connectionId;

}
