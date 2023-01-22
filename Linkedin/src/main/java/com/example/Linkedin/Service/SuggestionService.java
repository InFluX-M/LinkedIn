package com.example.Linkedin.Service;

import com.example.Linkedin.File.FileService;
import com.example.Linkedin.File.UserUtil;
import com.example.Linkedin.Model.*;
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
    private Graph graph;
    private Centrality centrality;

    public void createGraph() {
        graph = fileService.createGraph();
        centrality = new Centrality(graph);
    }

    public double getKatzCentralityScore(String id) {
        ArrayList<ArrayList<Node>> katz = centrality.katzCentrality(0.5);
        for (ArrayList<Node> nodes : katz) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    return node.value;
                }
            }
        }
        return 0;
    }

    public double getBetweennessCentralityScore(String id) {
        ArrayList<ArrayList<Node>> betweenness = centrality.betweennessCentrality();
        for (ArrayList<Node> nodes : betweenness) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    return node.value;
                }
            }
        }
        return 0;
    }

    public double getClosenessCentralityScore(String id) {
        ArrayList<ArrayList<Node>> closeness = centrality.closenessCentrality();
        for (ArrayList<Node> nodes : closeness) {
            for (Node node : nodes) {
                if (node.vertex.getElement().getId().equals(id)) {
                    return node.value;
                }
            }
        }
        return 0;
    }

    public double getScoreConnection(User user, Set<Vertex> component, String idConnection) {
        User connection = userService.checkUserId(idConnection);
        UserUtil userUtil = new UserUtil(user.getId(), user.getName(), user.getProfile_url(), user.getEmail(), user.getDateOfBirth().toString(),
                user.getUniversityLocation(), user.getField(), user.getWorkplace(), user.getSpecialities());

        UserUtil connectionUtil = new UserUtil(connection.getId(), connection.getName(), connection.getProfile_url(), connection.getEmail(), connection.getDateOfBirth().toString(),
                connection.getUniversityLocation(), connection.getField(), connection.getWorkplace(), connection.getSpecialities());

        double score = 0;
        score += (getKatzCentralityScore(idConnection)/1.5);
        score += (getBetweennessCentralityScore(idConnection)/60);
        score += (getClosenessCentralityScore(idConnection)*500);
        score += (component.contains(graph.getVertex(idConnection)) ? 50.0 : 0);
        score += (userUtil.getUniversityLocation().equals(connectionUtil.getUniversityLocation())) ? 200.0 : 0.0;
        score += (userUtil.getField().equals(connectionUtil.getField())) ? 100 : 0.0;
        score += (userUtil.getWorkplace().equals(connectionUtil.getWorkplace())) ? 200.0 : 0;

        int commonSpecialities = 0;
        for (String speciality : userUtil.getSpecialties()) {
            if (connectionUtil.getSpecialties().contains(speciality)) {
                commonSpecialities++;
            }
        }
        score += (commonSpecialities*50);
        return score;
    }

    public List<User> getSuggestions(String idUser) {
        User user = userService.checkUserId(idUser);
        Set<Vertex> component = graph.BFS(graph.getVertex(idUser));
        List<User> suggestion = new ArrayList<>();
        ArrayList<Map.Entry<User, Double>> scoresList = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();
        for (User suggest : allUsers) {
            if (!suggest.getId().equals(idUser) && !user.getConnections().contains(suggest)) {
                double score = getScoreConnection(user, component, suggest.getId());
                scoresList.add(new AbstractMap.SimpleEntry<>(suggest, score));
            }
        }

        Collections.sort(scoresList, new Comparator<Map.Entry<User, Double>>() {
            @Override
            public int compare(Map.Entry<User, Double> o1, Map.Entry<User, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < 10; i++) {
            suggestion.add(scoresList.get(i).getKey());
        }

        return suggestion;
    }
}
