package com.example.Linkedin.Model;

//import com.example.Linkedin.Model.response.ProfileResponse;
import com.example.Linkedin.Model.response.UserResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String field;
    private String workplace;
    private String universityLocation;
    private LocalDate dateOfBirth;
    private String profile_url;
    @ElementCollection
    private List<String> specialities;
    @ManyToMany
    @ToString.Exclude
    private Set<User> connections;
    @ManyToMany
    @ToString.Exclude
    private Set<User> requests;

    public UserResponse toUserResponse() {
        return new UserResponse(username, email, password);
    }

//    public ProfileResponse toProfileResponse() {
//        return new ProfileResponse(name, username, email, field, workplace, universityLocation, dateOfBirth, profile_url, specialities);
//    }

}