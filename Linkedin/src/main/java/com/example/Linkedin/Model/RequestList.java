package com.example.Linkedin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class RequestList {
    @Id
    @GeneratedValue
    private Integer id;

    private int size;

    @ManyToMany
    @JoinTable(
            name = "requests_users",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<User> requests;
}
