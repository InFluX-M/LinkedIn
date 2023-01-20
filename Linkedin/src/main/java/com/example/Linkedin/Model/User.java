package com.example.Linkedin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String field;
    private String workplace;
    private String universityLocation;
    private LocalDate dateOfBirth;
    @ElementCollection
    private List<String> specialities;
    @ManyToMany
    private Set<User> connections;
    @ManyToMany
    private Set<User> requests;
    private List<User> requests;
    private List<String> specialities;
    private List<String> connectionId;
    @Override
    public String toString() {
        return this.id;
    }
}