package com.example.Linkedin.File;

import com.example.Linkedin.Exception.UserNotFoundException;
import com.example.Linkedin.Model.Graph;
import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.Vertex;
import com.example.Linkedin.Model.response.UserProfile;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {

    private UserRepository userRepository;

    public List<UserResponse> readUsers() {
        String path = this.getClass().getResource("/users.json").getPath();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        Map<String, User> usersMap = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<UserUtil> users = mapper.readValue(new File(path), mapper.getTypeFactory().constructCollectionType(ArrayList.class, UserUtil.class));

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
                        .profile_url(userUtil.getProfile_pic())
                        .universityLocation(userUtil.getUniversityLocation())
                        .specialities(userUtil.getSpecialties())
                        .build();

                if (userUtil.getDateOfBirth().charAt(6) == '/') {
                    String date = userUtil.getDateOfBirth().substring(0, 5) + "0" + userUtil.getDateOfBirth().substring(5);
                    user.setDateOfBirth(LocalDate.parse(date, formatter));
                } else {
                    user.setDateOfBirth(LocalDate.parse(userUtil.getDateOfBirth(), formatter));
                }
                usersMap.put(user.getId(), user);
            }

            userRepository.saveAll(usersMap.values());

            // add connections
            for (UserUtil userUtil : users) {

                Set<User> connections = userUtil.getConnectionId().stream()
                        .map(id -> userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)))
                        .collect(Collectors.toSet());

                String id = userUtil.getId();
                usersMap.get(id).setConnections(connections);
            }

            System.out.println(usersMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.saveAll(usersMap.values()).stream().map(User::toUserResponse).collect(Collectors.toList());
    }

    public Graph createGraph() {
        List<User> list = userRepository.findAll();

        Graph graph = new Graph();

        for(User user : list) {
            Set<String> connectionIds = user.getConnections().stream().map(User::getId).collect(Collectors.toSet());
            UserProfile profileResponse = new UserProfile(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getField(), user.getWorkplace(), user.getUniversityLocation(), user.getDateOfBirth(), user.getProfile_url(), user.getSpecialities(), connectionIds);
            graph.insertVertex(profileResponse);
        }

        for(Vertex vertex : graph.vertices()) {
            for(String connectionID : vertex.getElement().getConnectionId())
            {
                graph.insertEdge(vertex, graph.getVertex(connectionID), vertex.getElement().getId() + "-" + connectionID);
            }
        }

        return graph;
    }

}