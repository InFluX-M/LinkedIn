package com.example.Linkedin.File;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import com.example.Linkedin.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {

    private UserService userService;
    private UserRepository userRepository;

    public List<UserResponse> readUsers() {
        String path = "/media/influx/Programming/Projects/project-final-random/Linkedin/src/main/resources/users.json";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        List<User> usersList = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<UserUtil> users = mapper.readValue(new java.io.File(path), mapper.getTypeFactory().constructCollectionType(ArrayList.class, UserUtil.class));

            for (UserUtil userUtil : users) {
                User user = User.builder()
                        .id(userUtil.getId())
                        .name(userUtil.getName())
                        .username(userUtil.getName().split(" ")[0])
                        .password(userUtil.getDateOfBirth().split("/")[0])
                        .email(userUtil.getEmail())
                        .dateOfBirth(LocalDate.now())
                        .field(userUtil.getField())
                        .workplace(userUtil.getWorkplace())
                        .universityLocation(userUtil.getUniversityLocation())
                        .connections(userUtil.getConnectionId().stream().map(userService::getUser).collect(Collectors.toSet()))
                        .specialities(userUtil.getSpecialties())
                        .build();

                if(userUtil.getDateOfBirth().charAt(6) == '/') {
                    String date = userUtil.getDateOfBirth().substring(0, 5) + "0" + userUtil.getDateOfBirth().substring(5);
                    user.setDateOfBirth(LocalDate.parse(date, formatter));
                }
                else {
                    user.setDateOfBirth(LocalDate.parse(userUtil.getDateOfBirth(), formatter));
                }
                usersList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.saveAll(usersList).stream().map(User::toUserResponse).collect(Collectors.toList());
    }

}
