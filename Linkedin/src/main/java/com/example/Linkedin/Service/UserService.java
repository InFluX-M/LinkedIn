//package com.example.Linkedin.Service;
//
//import com.example.Linkedin.Exception.InvalidUsernameException;
//import com.example.Linkedin.Model.User;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserService {
//
//    private static final List<User> allUsers = new ArrayList<>();
//    private static int ID_COUNTER = 0;
//    private static User currentUser;
//
//    public User signup(String username, String name, String email, String password, String field, String workplace,
//                       String universityLocation, String dateOfBirth) {
//
//        if (!checkUsername(username)) {
//            throw new InvalidUsernameException();
//        }
//
//        User user = User.builder()
//                .id(String.valueOf(ID_COUNTER++))
//                .username(username)
//                .name(name)
//                .email(email)
//                .password(password)
//                .field(field)
//                .workplace(workplace)
//                .universityLocation(universityLocation)
//                .dateOfBirth(LocalDate.parse(dateOfBirth))
//                .build();
//
//        allUsers.add(user);
//        return user;
//    }
//
//    private boolean checkUsername(String username) {
//        for (User user : allUsers) {
//            if (user.getUsername().equals(username)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public User login(String email, String password) {
//        User user = allUsers.stream()
//                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        currentUser = user;
//        return user;
//    }
//
//    public void logout() {
//        currentUser = null;
//    }
//
//    public void sendRequest(String username) {
//        User user = getUserByName(username);
//        if (currentUser != null) {
//            if (!user.getRequests().contains(currentUser))
//                user.getRequests().add(currentUser);
//        } else {
//            throw new RuntimeException("You are not logged in");
//        }
//    }
//
//    public void acceptRequest(String username) {
//        User user = getUserByUsername(username);
//        if (currentUser != null) {
//            if (currentUser.getRequests().contains(user)) {
//                currentUser.getRequests().remove(user);
//                currentUser.getConnectionId().add(user.getId());
//                user.getConnectionId().add(currentUser.getId());
//            }
//        } else {
//            throw new RuntimeException("You are not logged in");
//        }
//    }
//
//    public void addSpeciality(String speciality) {
//        if (currentUser != null) {
//            if (!currentUser.getSpecialities().contains(speciality))
//                currentUser.getSpecialities().add(speciality);
//        } else {
//            throw new RuntimeException("You are not logged in");
//        }
//    }
//
//    public static List<User> getAllUsers() {
//        return allUsers;
//    }
//
//    public User getUserByUsername(String username) {
//        return allUsers.stream()
//                .filter(u -> u.getUsername().equals(username))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    public User getUserByName(String name) {
//        return allUsers.stream()
//                .filter(u -> u.getName().equals(name))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    public void showUserProfile() {
//        System.out.println("Name: " + currentUser.getName());
//        System.out.println("Email: " + currentUser.getEmail());
//        System.out.println("Field: " + currentUser.getField());
//        System.out.println("Workplace: " + currentUser.getWorkplace());
//        System.out.println("University Location: " + currentUser.getUniversityLocation());
//        System.out.println("Date of Birth: " + currentUser.getDateOfBirth());
//        System.out.println("Specialities: " + currentUser.getSpecialities());
//        System.out.println("Connections: " + currentUser.getConnectionId());
//    }
//
//    public void showAllUsers() {
//        for (User user : allUsers) {
//            System.out.println("Name: " + currentUser.getName());
//            System.out.println("Email: " + currentUser.getEmail());
//            System.out.println("Field: " + currentUser.getField());
//            System.out.println("Workplace: " + currentUser.getWorkplace());
//            System.out.println("University Location: " + currentUser.getUniversityLocation());
//            System.out.println("Date of Birth: " + currentUser.getDateOfBirth());
//            System.out.println("Specialities: " + currentUser.getSpecialities());
//            System.out.println("Connections: " + currentUser.getConnectionId());
//        }
//    }
//
//    public void suggestions() {
//        // todo
//    }
//}
