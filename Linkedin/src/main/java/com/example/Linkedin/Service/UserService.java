package com.example.Linkedin.Service;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserSignup;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    public static User loggedInUser;

    private final UserRepository userRepository;

    public UserResponse login(String username, String password) {
        loggedInUser = userRepository.findByUsernameAndPassword(username, password);
        return loggedInUser.toUserResponse();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<User> getConnections(String username) {
        return userRepository.findByUsername(username).get(0).getConnections();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get(0);
    }

    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email).toUserResponse();
    }

    public UserResponse addSpeciality(String speciality) {
        loggedInUser.getSpecialities().add(speciality);
        userRepository.save(loggedInUser);
        return loggedInUser.toUserResponse();
    }

    public UserResponse removeSpeciality(String speciality) {
        loggedInUser.getSpecialities().remove(speciality);
        userRepository.save(loggedInUser);
        return loggedInUser.toUserResponse();
    }

    public UserResponse signUp(UserSignup userSignup) {
        User user = User.builder()
                .username(userSignup.getUsername())
                .email(userSignup.getEmail())
                .password(userSignup.getPassword())
                .id(userSignup.getId())
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

    public Void addConnection(String connection) {
        User user2 = checkUserId(connection);
        loggedInUser.getConnections().add(user2);
        user2.getConnections().add(loggedInUser);
        userRepository.save(loggedInUser);
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
