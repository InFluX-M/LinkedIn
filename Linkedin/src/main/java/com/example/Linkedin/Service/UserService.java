package com.example.Linkedin.Service;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserSignup;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email).toUserResponse();
    }

    public UserResponse addSpeciality(String username, String speciality) {
        User user = checkUserId(username);
        user.getSpecialities().add(speciality);
        userRepository.save(user);
        return user.toUserResponse();
    }

    public UserResponse removeSpeciality(String username, String speciality) {
        User user = checkUserId(username);
        user.getSpecialities().remove(speciality);
        userRepository.save(user);
        return user.toUserResponse();
    }

    public UserResponse signUp(UserSignup userSignup) {
        User user = User.builder()
                .username(userSignup.getUsername())
                .email(userSignup.getEmail())
                .password(userSignup.getPassword())
                .build();
        return userRepository.save(user).toUserResponse();
    }

    public UserResponse updateUser(User user) {
        return userRepository.save(user).toUserResponse();
    }

    public UserResponse deleteUser(User user) {
        userRepository.delete(user);
        return user.toUserResponse();
    }

    public Void addConnection(String user, String connection) {
        User user1 = checkUserId(user);
        User user2 = checkUserId(connection);
        user1.getConnections().add(user2);
        user2.getConnections().add(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        return null;
    }

    public Void removeConnection(String user, String connection) {
        User user1 = checkUserId(user);
        User user2 = checkUserId(connection);
        user1.getConnections().remove(user2);
        user2.getConnections().remove(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        return null;
    }

    public Void addRequest(String user, String request) {
        User user1 = checkUserId(user);
        User user2 = checkUserId(request);
        user2.getRequests().add(user1);
        userRepository.save(user2);
        return null;
    }

    public ResponseEntity<UserResponse> removeRequest(String user, String request) {
        User user1 = checkUserId(user);
        User user2 = checkUserId(request);
        user2.getRequests().remove(user1);
        userRepository.save(user2);
        return ResponseEntity.ok(userRepository.save(user1).toUserResponse());
    }

    public boolean sentRequest(String user, String request) {
        User user1 = checkUserId(user);
        User user2 = checkUserId(request);
        return user2.getRequests().contains(user1);
    }

    public User checkUserId(String Id) {
        Optional<User> loaded = userRepository.findById(Id);
        if (loaded.isEmpty())
            throw new EntityNotFoundException("User not found");
        return loaded.get();
    }
}
