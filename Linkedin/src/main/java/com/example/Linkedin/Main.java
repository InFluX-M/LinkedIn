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
            User user = new User(us.getId(), us.getName(), us.getEmail(), us.getField(), us.getWorkplace(), us.getUniversityLocation(), us.getDateOfBirth(), us.getSpecialties(), us.getConnectionId());
            graph.insertVertex(user);
        }

        for(Vertex vertex : graph.vertices()) {
            for(String connectionID : vertex.getElement().getConnectionId())
            {
                graph.insertEdge(vertex, graph.getVertex(connectionID), vertex.getElement().getId() + "-" + connectionID);
            }
        }

//        graph.printBeautified();
//        System.out.println("--------------");
//        graph.bfsLevels(graph.getVertex("A"), 8);

//        graph.identifyComponentsDFS();
//        for (HashSet<Vertex> s : graph.getComponents().values()){
//            for (Vertex v : s) {
//                System.out.println(v.getElement());
//            }
//            System.out.println("--------------------------------");
//        }
//        System.out.println("sal");
//        System.out.println(graph.bfsLevels(graph.getVertex("A"), 8));

        Centrality c = new Centrality(graph);
        for(ArrayList<Node> n :  c.degreeCentrality())
        {
            for(Node node : n){
                System.out.println(node.vertex.getElement() + " " + node.value + " " + node.vertex.getEdges().size());
            }
            System.out.println("--------------------------------");
        }
        System.out.println("SAlaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        for(ArrayList<Node> n :  c.closenessCentrality())
        {
            for(Node node : n){
                System.out.println(node.vertex.getElement() + " " + node.value + " " + node.vertex.getEdges().size());
            }
            System.out.println("--------------------------------");
        }
        System.out.println("SAlaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        for(ArrayList<Node> n :  c.betweennessCentrality())
        {
            for(Node node : n){
                System.out.println(node.vertex.getElement() + " " + node.value + " " + node.vertex.getEdges().size());
            }
            System.out.println("--------------------------------");
        }


        System.out.println("HHIi");
    }
}
