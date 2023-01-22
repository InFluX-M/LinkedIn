package com.example.Linkedin.Service;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MLService {

    private final UserService userService;

    public List<User> getAnomalyDetection() throws Exception {
        ArrayList<String> users = getDataAnomalyDetection();
        List<User> usersList = new ArrayList<>();
        for (String id : users) {
            usersList.add(userService.checkUserId(id));
        }
        return usersList;
    }

    public List<List<User>> getClusters(String type) throws Exception {
        ArrayList<ArrayList<String>> clusters = getDataClustering(type);
        List<List<User>> users = new ArrayList<>();
        for (ArrayList<String> cluster : clusters) {
            List<User> clusterUsers = new ArrayList<>();
            for (String id : cluster) {
                int userId = Integer.parseInt(id);
                userId++;
                String idString = String.valueOf(userId);
                clusterUsers.add(userService.checkUserId(idString));
            }
            users.add(clusterUsers);
        }
        return users;
    }

    public ArrayList<String> getDataAnomalyDetection() throws Exception {
        String urlToRead = "http://127.0.0.1:8000/api/anomaly_detection";
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        String aim = result.toString();

        ArrayList<String> anomalies = new ArrayList<>();
        for (String c : aim.split("[\\[\\]]")) {
            if (c.isEmpty()) {
                continue;
            }
            ArrayList<String> s = new ArrayList<>(Arrays.asList(c.split(", ")));
            if (s.size() > 1) {
                anomalies.add(s.get(0));
            }
        }
        return anomalies;
    }

    public ArrayList<ArrayList<String>> getDataClustering(String type) throws Exception {

        String urlToRead = "http://127.0.0.1:8000/api/clustering_" + type;
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        String aim = result.toString();
        ArrayList<ArrayList<String>> clusters = new ArrayList<>();

        for (String c : aim.split("[\\[\\]]")) {
            if (c.isEmpty()) {
                continue;
            }
            ArrayList<String> cluster = new ArrayList<>(Arrays.asList(c.split(", ")));
            if (cluster.size() > 1) {
                clusters.add(cluster);
            }
        }

        return clusters;
    }

    public List<User> getRecommendations(String id, String type) throws Exception {
        List<List<User>> users = getClusters(type);
        User user = userService.checkUserId(id);
        for (List<User> cluster : users) {
            if (cluster.contains(user)) {
               return cluster;
            }
        }
        return null;
    }

}
