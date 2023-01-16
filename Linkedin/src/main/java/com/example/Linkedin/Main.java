package com.example.Linkedin;

import com.example.Linkedin.File.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String jsonArray = Files.readString(Path.of("src/main/resources/users.json"));
            System.out.println(jsonArray);

            ObjectMapper objectMapper = new ObjectMapper();
            List<UserUtil> list = objectMapper.readValue(jsonArray, new TypeReference<>() {
            });

            for (UserUtil user : list) {
                System.out.println(user);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
