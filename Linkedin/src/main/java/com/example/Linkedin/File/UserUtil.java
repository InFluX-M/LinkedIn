package com.example.Linkedin.File;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
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

    public UserUtil(String id, String name, String profile_pic, String email, String dateOfBirth, String universityLocation, String field, String workplace, List<String> specialties) {
        this.id = id;
        this.name = name;
        this.profile_pic = profile_pic;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.universityLocation = universityLocation;
        this.field = field;
        this.workplace = workplace;
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return "UserUtil{" +
                "id='" + id + '\'' +
                '}';
    }
}
