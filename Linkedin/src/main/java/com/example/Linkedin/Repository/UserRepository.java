package com.example.Linkedin.Repository;

import com.example.Linkedin.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);
}
