package com.example.Linkedin.Model;

import com.example.Linkedin.Model.response.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private final UserProfile element;
    private final Map<Vertex, Edge> edges;

    public Vertex(UserProfile element) {
        this.element = element;
        edges = new HashMap<>();
    }

    public UserProfile getElement() {
        return element;
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }
}
