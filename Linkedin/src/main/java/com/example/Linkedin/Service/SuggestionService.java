package com.example.Linkedin.Service;

import com.example.Linkedin.File.FileService;
import com.example.Linkedin.File.UserUtil;
import com.example.Linkedin.Model.*;
import com.example.Linkedin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public HashSet<Vertex> getComponentUser(String id) {
        HashMap<String, HashSet<Vertex>> components = graph.getComponents();
        for (String key : components.keySet()) {
            for (Vertex vertex : components.get(key)) {
                if (vertex.getElement().getId().equals(id)) {
                    return components.get(key);
                }
            }
        }
        return null;
    }

    public double getComponentScore(String idUser, String idConnection) {
        HashMap<String, HashSet<Vertex>> components = graph.getComponents();
        HashSet<Vertex> componentId = getComponentUser(idUser);
        return (componentId.contains(graph.getVertex(idConnection))) ? 50.0 : 0;
    }

    public double getScoreConnection(String idUser, String idConnection) {
        User user = userService.checkUserId(idUser);
        User connection = userService.checkUserId(idUser);
        UserUtil userUtil = new UserUtil(user.getId(), user.getName(), user.getProfile_url(), user.getEmail(), user.getDateOfBirth().toString(),
                user.getUniversityLocation(), user.getField(), user.getWorkplace(), user.getSpecialities());

        UserUtil connectionUtil = new UserUtil(connection.getId(), connection.getName(), connection.getProfile_url(), connection.getEmail(), connection.getDateOfBirth().toString(),
                connection.getUniversityLocation(), connection.getField(), connection.getWorkplace(), connection.getSpecialities());

        double score = 0;
        score += (getKatzCentralityScore(idConnection)/1.5);
        score += (getBetweennessCentralityScore(idConnection)/60);
        score += (getClosenessCentralityScore(idConnection)*500);
        score += getComponentScore(idUser, idConnection);
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


}
