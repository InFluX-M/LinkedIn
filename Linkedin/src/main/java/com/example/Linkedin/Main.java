package com.example.Linkedin;

import com.example.Linkedin.File.UserUtil;
import com.example.Linkedin.Model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<UserUtil> list;
        try {
            String jsonArray = Files.readString(Path.of("/media/influx/Programming/Projects/project-final-random/Linkedin/src/main/resources/users.json"));
            System.out.println(jsonArray);

            ObjectMapper objectMapper = new ObjectMapper();
            list = objectMapper.readValue(jsonArray, new TypeReference<>() {
            });

            for (UserUtil user : list) {
                System.out.println(user);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Graph graph = new Graph();

        for(int i = 0; i < 2000; i++){
            UserUtil us = list.get(i);
            User user = new User();
            user.setId(us.getId());
            user.setName(us.getName());
            user.setEmail(us.getEmail());
            user.setField(us.getField());
            user.setWorkplace(us.getWorkplace());
            user.setUniversityLocation(us.getUniversityLocation());
            user.setSpecialities(us.getSpecialties());
            user.setConnectionId(us.getConnectionId());
            graph.insertVertex(user);
        }

        for(Vertex vertex : graph.vertices()) {
            for(String connectionID : vertex.getElement().getConnectionId())
            {
                graph.insertEdge(vertex, graph.getVertex(connectionID), vertex.getElement().getId() + "-" + connectionID);
            }
        }

    }
}
