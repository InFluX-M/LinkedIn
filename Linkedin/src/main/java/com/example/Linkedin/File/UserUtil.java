package com.example.Linkedin.File;

import lombok.Data;

import java.util.List;

@Data
public class UserUtil {
    // is used for reading data from json file
    private String id;
    private String name;
    private String dateOfBirth;
    private String universityLocation;
    private String field;
    private String workplace;
    List<String> specialties;
    List<String> connectionId;
}
