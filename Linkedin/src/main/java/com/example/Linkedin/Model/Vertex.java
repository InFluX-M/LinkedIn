package com.example.Linkedin.Model;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private User element;
    private final Map<Vertex, Edge> edges;

    public Vertex(User element, boolean isDirected) {
        this.element = element;
        edges = new HashMap<>();
    }

    public User getElement() {
        return element;
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }

    public void setElement(User element) {
        this.element = element;
    }
}
