package com.example.Linkedin.Model;

import com.example.Linkedin.File.UserUtil;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private final UserUtil element;
    private final Map<Vertex, Edge> edges;

    public Vertex(UserUtil element) {
        this.element = element;
        edges = new HashMap<>();
    }

    public UserUtil getElement() {
        return element;
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }
}
