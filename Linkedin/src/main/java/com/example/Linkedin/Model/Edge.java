package com.example.Linkedin.Model;

class Edge {
    private String element;
    private final Vertex[] endpoints;

    public Edge(Vertex u, Vertex v, String element) {
        this.element = element;
        endpoints = (Vertex[]) new Vertex[]{u, v};
    }

    public Vertex[] getEndpoints() {
        return endpoints;
    }

    public Vertex getOpposite(Vertex v) {
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
