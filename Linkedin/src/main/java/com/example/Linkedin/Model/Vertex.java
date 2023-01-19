package com.example.Linkedin.Model;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private final User element;
    private final Map<Vertex, Edge> edges;

    public Vertex(User element) {
        this.element = element;
        edges = new HashMap<>();
    }

    public User getElement() {
        return element;
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }
}
