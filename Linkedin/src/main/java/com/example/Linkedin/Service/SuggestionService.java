package com.example.Linkedin.Service;

import com.example.Linkedin.File.FileService;
import com.example.Linkedin.File.UserUtil;
import com.example.Linkedin.Model.*;
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

    public double getKatzCentralityScore(String id, ArrayList<ArrayList<Node>> katz) {
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

    public double getBetweennessCentralityScore(String id, ArrayList<ArrayList<Node>> betweenness) {
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

    public double getClosenessCentralityScore(String id, ArrayList<ArrayList<Node>> closeness) {
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

    public double getScoreConnection(Set<String> impact, User user, Set<Vertex> component, String idConnection, Graph graph, ArrayList<ArrayList<Node>> katz, ArrayList<ArrayList<Node>> betweenness, ArrayList<ArrayList<Node>> closeness) {
        User connection = userService.checkUserId(idConnection);

        UserUtil userUtil = UserUtil.builder()
                .name(user.getName())
                .field(user.getField())
                .dateOfBirth(user.getDateOfBirth().toString())
                .email(user.getEmail())
                .workplace(user.getWorkplace())
                .specialties(user.getSpecialities())
                .universityLocation(user.getUniversityLocation())
                .profile_pic(user.getProfile_url())
                .build();

        UserUtil connectionUtil = UserUtil.builder()
                .name(connection.getName())
                .field(connection.getField())
                .dateOfBirth(connection.getDateOfBirth().toString())
                .email(connection.getEmail())
                .workplace(connection.getWorkplace())
                .specialties(connection.getSpecialities())
                .universityLocation(connection.getUniversityLocation())
                .profile_pic(connection.getProfile_url())
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

        if(impact.contains("Component"))
            score += (component.contains(graph.getVertex(idConnection)) ? 100.0 : 0);
        else
            score += (component.contains(graph.getVertex(idConnection)) ? 30.0 : 0);


        if(impact.contains("Field"))
            score += (userUtil.getField().equals(connectionUtil.getField()) ? 150.0 : 0);
        else
            score += (userUtil.getField().equals(connectionUtil.getField()) ? 100.0 : 0);


        if(impact.contains("Workplace"))
            score += (userUtil.getWorkplace().equals(connectionUtil.getWorkplace()) ? 250.0 : 0);
        else
            score += (userUtil.getWorkplace().equals(connectionUtil.getWorkplace()) ? 150.0 : 0);


        if(impact.contains("University"))
            score += (userUtil.getUniversityLocation().equals(connectionUtil.getUniversityLocation()) ? 250.0 : 0);
        else
            score += (userUtil.getUniversityLocation().equals(connectionUtil.getUniversityLocation()) ? 150.0 : 0);


        int commonSpecialities = 0;
        for (String speciality : userUtil.getSpecialties()) {
            if (connectionUtil.getSpecialties().contains(speciality)) {
                commonSpecialities++;
            }
        }
        score += (commonSpecialities*50);
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

        List<User> allUsers = userRepository.findAll();
        for (User suggest : allUsers) {
            if (!suggest.getId().equals(idUser) && !user.getConnections().contains(suggest)) {
                double score = getScoreConnection(impactSet, user, component, suggest.getId(), graph, katz, betweenness, closeness);
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
        }

        return suggestion;
    }
}
