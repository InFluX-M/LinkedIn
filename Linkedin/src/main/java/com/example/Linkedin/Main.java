package com.example.Linkedin;

import com.example.Linkedin.Model.Graph;
import com.example.Linkedin.Model.User;

public class Main {
    public static void main(String[] args) {
//        try {
//            String jsonArray = Files.readString(Path.of("src/main/resources/users.json"));
//            System.out.println(jsonArray);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<UserUtil> list = objectMapper.readValue(jsonArray, new TypeReference<>() {
//            });
//
//            for (UserUtil user : list) {
//                System.out.println(user);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("Hello World");

        Graph<String, String> graph = new Graph<>(true);
        graph.insertVertex(new User("A"));
        graph.insertVertex(new User("B"));
        graph.insertVertex(new User("C"));
        graph.insertVertex(new User("D"));
        graph.insertVertex(new User("E"));
        graph.insertVertex(new User("F"));
        graph.insertVertex(new User("G"));
        graph.insertVertex(new User("H"));
        graph.insertVertex(new User("I"));
        graph.insertVertex(new User("J"));

        graph.insertEdge(graph.getVertex("A"), graph.getVertex("B"), "AB");
        graph.insertEdge(graph.getVertex("A"), graph.getVertex("C"), "AC");
        graph.insertEdge(graph.getVertex("B"), graph.getVertex("D"), "BD");
        graph.insertEdge(graph.getVertex("B"), graph.getVertex("E"), "BE");
        graph.insertEdge(graph.getVertex("C"), graph.getVertex("E"), "CE");
        graph.insertEdge(graph.getVertex("C"), graph.getVertex("F"), "CF");
        graph.insertEdge(graph.getVertex("D"), graph.getVertex("G"), "DG");
        graph.insertEdge(graph.getVertex("D"), graph.getVertex("E"), "DE");
        graph.insertEdge(graph.getVertex("E"), graph.getVertex("G"), "EG");
        graph.insertEdge(graph.getVertex("E"), graph.getVertex("F"), "EF");
        graph.insertEdge(graph.getVertex("E"), graph.getVertex("H"), "EH");
        graph.insertEdge(graph.getVertex("F"), graph.getVertex("I"), "FI");
        graph.insertEdge(graph.getVertex("G"), graph.getVertex("J"), "GJ");

        graph.printBeautified();
        System.out.println("--------------");
        graph.bfsLevels(graph.getVertex("A"), 8);


    }
}
