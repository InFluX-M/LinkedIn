package com.example.Linkedin.Service;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserSignup;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> getUser(String username) {
        return ResponseEntity.ok(userRepository.findByUsername(username).toUserResponse());
    }

    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email).toUserResponse());
    }

    public ResponseEntity<UserResponse> addSpeciality(String username, String speciality) {
        User user = userRepository.findByUsername(username);
        user.getSpecialities().add(speciality);
        userRepository.save(user);
        return ResponseEntity.ok(user.toUserResponse());
    }

    public ResponseEntity<UserResponse> removeSpeciality(String username, String speciality) {
        User user = userRepository.findByUsername(username);
        user.getSpecialities().remove(speciality);
        userRepository.save(user);
        return ResponseEntity.ok(user.toUserResponse());
    }

    public ResponseEntity<UserResponse> signUp(UserSignup userSignup) {
        User user = User.builder()
                .username(userSignup.getUsername())
                .email(userSignup.getEmail())
                .password(userSignup.getPassword())
                .build();
        return ResponseEntity.ok(userRepository.save(user).toUserResponse());
    }

    public ResponseEntity<UserResponse> updateUser(User user) {
        return ResponseEntity.ok(userRepository.save(user).toUserResponse());
    }

    public ResponseEntity<UserResponse> deleteUser(User user) {
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> addConnection(String user, String connection) {
        User user1 = userRepository.findByUsername(user);
        User user2 = userRepository.findByUsername(connection);
        user1.getConnections().add(user2);
        user2.getConnections().add(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removeConnection(String user, String connection) {
        User user1 = userRepository.findByUsername(user);
        User user2 = userRepository.findByUsername(connection);
        user1.getConnections().remove(user2);
        user2.getConnections().remove(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> addRequest(String user, String request) {
        User user1 = userRepository.findByUsername(user);
        User user2 = userRepository.findByUsername(request);
        user2.getRequests().add(user1);
        userRepository.save(user2);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UserResponse> removeRequest(String user, String request) {
        User user1 = userRepository.findByUsername(user);
        User user2 = userRepository.findByUsername(request);
        user2.getRequests().remove(user1);
        userRepository.save(user2);
        return ResponseEntity.ok(userRepository.save(user1).toUserResponse());
    }

    public boolean sentRequest(String user, String request) {
        User user1 = userRepository.findByUsername(user);
        User user2 = userRepository.findByUsername(request);
        return user2.getRequests().contains(user1);
    }
}
