package com.example.Linkedin.Service;

import com.example.Linkedin.File.FileService;
import com.example.Linkedin.File.UserUtil;
import com.example.Linkedin.Model.*;
import com.example.Linkedin.Model.response.UserProfile;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SuggestionService {
    private final FileService fileService;
    private final UserService userService;
    private final UserRepository userRepository;

    public Graph createGraph() {
        Graph graph;
        graph = fileService.createGraph();
        System.out.println("Graph created");
        return graph;
    }

    private double getKatzCentralityScore(String id, ArrayList<ArrayList<Node>> katz) {
        for (ArrayList<Node> nodes : katz) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    System.out.println("Katz centrality)");
                    return node.value;
                }
            }
        }
        return 0;
    }

    private double getBetweennessCentralityScore(String id, ArrayList<ArrayList<Node>> betweenness) {
        for (ArrayList<Node> nodes : betweenness) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    System.out.println("Betweenness centrality)");
                    return node.value;
                }
            }
        }
        return 0;
    }

    private double getClosenessCentralityScore(String id, ArrayList<ArrayList<Node>> closeness) {
        for (ArrayList<Node> nodes : closeness) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    System.out.println("Clos centrality)");
                    return node.value;
                }
            }
        }
        return 0;
    }

    private double getScoreConnection(List<List<Vertex>> levels, Set<String> impact, User user, Set<Vertex> component, String idConnection, Graph graph, ArrayList<ArrayList<Node>> katz, ArrayList<ArrayList<Node>> betweenness, ArrayList<ArrayList<Node>> closeness) {
        User connection = userService.checkUserId(idConnection);

        UserProfile userProfile = UserProfile.builder()
                .id(user.getId())
                .name(user.getName())
                .field(user.getField())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .workplace(user.getWorkplace())
                .specialities(user.getSpecialities())
                .universityLocation(user.getUniversityLocation())
                .profile_url(user.getProfile_url())
                .build();

        UserProfile connectionProfile = UserProfile.builder()
                .id(connection.getId())
                .name(connection.getName())
                .field(connection.getField())
                .dateOfBirth(connection.getDateOfBirth())
                .email(connection.getEmail())
                .workplace(connection.getWorkplace())
                .specialities(connection.getSpecialities())
                .universityLocation(connection.getUniversityLocation())
                .profile_url(connection.getProfile_url())
                .build();


        double score = 0;


        if(impact.contains("Centrality"))
        {
            score += getKatzCentralityScore(idConnection, katz)/1.2;
            score += getBetweennessCentralityScore(idConnection, betweenness)/300;
            score += getClosenessCentralityScore(idConnection, closeness)*700;
        }
        else
        {
            score += (getKatzCentralityScore(idConnection, katz)/1.5);
            score += (getBetweennessCentralityScore(idConnection, betweenness)/500);
            score += (getClosenessCentralityScore(idConnection, closeness)*500);
        }

        int lvlConnection = 10;
        int lvl = 0;
        for(List<Vertex> level : levels) {
            if(level.contains(graph.getVertex(idConnection))) {
                lvlConnection = lvl;
            }
            lvl++;
        }

        if(impact.contains("Component"))
        {
            score += (component.contains(graph.getVertex(idConnection)) ? 100.0 : 0);
            score += (10 - lvlConnection)*50;
        }
        else
        {
            score += (component.contains(graph.getVertex(idConnection)) ? 30.0 : 0);
            score += (10 - lvlConnection)*15;

        }

        if(impact.contains("Field"))
            score += (userProfile.getField().equals(connectionProfile.getField()) ? 150.0 : 0);
        else
            score += (userProfile.getField().equals(connectionProfile.getField()) ? 100.0 : 0);


        if(impact.contains("Workplace"))
            score += (userProfile.getWorkplace().equals(connectionProfile.getWorkplace()) ? 250.0 : 0);
        else
            score += (userProfile.getWorkplace().equals(connectionProfile.getWorkplace()) ? 150.0 : 0);

        if(impact.contains("University"))
            score += (userProfile.getUniversityLocation().equals(connectionProfile.getUniversityLocation()) ? 250.0 : 0);
        else
            score += (userProfile.getUniversityLocation().equals(connectionProfile.getUniversityLocation()) ? 150.0 : 0);

        int commonSpecialities = 0;
        for (String speciality : userProfile.getSpecialities()) {
            if (connectionProfile.getSpecialities().contains(speciality)) {
                commonSpecialities++;
            }
        }

        if(impact.contains("Specialties")) {
            score += (commonSpecialities*100);
        }
        else {
            score += (commonSpecialities*30);
        }

        return score;
    }

    public List<User> getSuggestions(String idUser, String impact) {

        Graph graph = createGraph();
        User user = userService.checkUserId(idUser);
        Set<Vertex> component = graph.BFS(graph.getVertex(idUser));
        List<User> suggestion = new ArrayList<>();
        ArrayList<Map.Entry<User, Double>> scoresList = new ArrayList<>();

        Centrality centrality = new Centrality(graph);
        ArrayList<ArrayList<Node>> katz = centrality.katzCentrality(0.5);
        ArrayList<ArrayList<Node>> betweenness = centrality.betweennessCentrality();
        ArrayList<ArrayList<Node>> closeness = centrality.closenessCentrality();

        Set<String> impactSet = new HashSet<>(Arrays.asList(impact.split(",")));
        List<List<Vertex>> levels = graph.bfsLevels(graph.getVertex(idUser), 10);

        List<User> allUsers = userRepository.findAll();
        for (User suggest : allUsers) {
            if (!suggest.getId().equals(idUser) && !user.getConnections().contains(suggest)) {
                double score = getScoreConnection(levels, impactSet, user, component, suggest.getId(), graph, katz, betweenness, closeness);
                scoresList.add(new AbstractMap.SimpleEntry<>(suggest, score));
            }
        }

        scoresList.sort(new Comparator<Map.Entry<User, Double>>() {
            @Override
            public int compare(Map.Entry<User, Double> o1, Map.Entry<User, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < 10; i++) {
            suggestion.add(scoresList.get(i).getKey());
            System.out.println(scoresList.get(i).getKey().getName() + " " + scoresList.get(i).getValue());
            System.out.println(getClosenessCentralityScore(scoresList.get(i).getKey().getId(), closeness));
            System.out.println(getBetweennessCentralityScore(scoresList.get(i).getKey().getId(), betweenness));
            System.out.println(getKatzCentralityScore(scoresList.get(i).getKey().getId(), katz));
            int lvl = 0;
            for(List<Vertex> level : levels) {
                if(level.contains(graph.getVertex(scoresList.get(i).getKey().getId()))) {
                    System.out.println("Level: " + lvl);
                }
                lvl++;
            }
        }

        return suggestion;
    }
    
    public List<User> getInfluenceUsers() {
        Graph graph = createGraph();
        Centrality centrality = new Centrality(graph);
        ArrayList<ArrayList<Node>> katz = centrality.katzCentrality(0.5);
        ArrayList<ArrayList<Node>> betweenness = centrality.betweennessCentrality();
        ArrayList<ArrayList<Node>> closeness = centrality.closenessCentrality();

        List<User> allUsers = userRepository.findAll();
        ArrayList<Map.Entry<User, Double>> scoresList = new ArrayList<>();

        for (User influence : allUsers) {
            double score = 0.0;
            score += (getKatzCentralityScore(influence.getId(), katz)/1.5);
            score += (getBetweennessCentralityScore(influence.getId(), betweenness)/500);
            score += (getClosenessCentralityScore(influence.getId(), closeness)*500);
            scoresList.add(new AbstractMap.SimpleEntry<>(influence, score));
        }

        scoresList.sort(new Comparator<Map.Entry<User, Double>>() {
            @Override
            public int compare(Map.Entry<User, Double> o1, Map.Entry<User, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        List<User> influenceUsers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            influenceUsers.add(scoresList.get(i).getKey());
        }

        return influenceUsers;
    }
}
