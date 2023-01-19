package com.example.Linkedin.Model;

import java.util.*;

public class Graph<V, E> {

    private final boolean isDirected;
    private final List<Vertex> vertices;
    private final List<Edge> edges;

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    public boolean isDirected() {
        return isDirected;
    }

    public int numVertices() {
        return vertices.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public List<Vertex> vertices() {
        return vertices;
    }

    public List<Edge> edges() {
        return edges;
    }

    public int degree(Vertex v) {
        return v.getEdges().size();
    }

    public Iterable<Edge> edges(Vertex v) {
        return v.getEdges().values();
    }

    public Edge getEdge(Vertex u, Vertex v) {
        return u.getEdges().get(v);
    }

    public Vertex[] endVertices(Edge e) {
        return e.getEndpoints();
    }

    public Vertex opposite(Vertex v, Edge e) {
        Vertex[] endpoints = e.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    public Vertex insertVertex(User element) {
        Vertex v = new Vertex(element, isDirected);
        vertices.add(v);
        return v;
    }

    public Edge insertEdge(Vertex u, Vertex v, String element) {
        if (getEdge(u, v) == null) {
            Edge e = new Edge(u, v, element);
            edges.add(e);
            u.getEdges().put(v, e);
            v.getEdges().put(u, e);
            return e;
        } else {
            throw new IllegalArgumentException("Edge from u to v exists");
        }
    }

    public Vertex getVertex(String id) {
        for (Vertex v : vertices) {
            if (Objects.equals(v.getElement().getId(), id)) {
                return v;
            }
        }
        return null;
    }

    public void printBeautified() {
        for (Vertex v : vertices) {
            System.out.println(v.getElement() + " -> " + v.getEdges().keySet().stream().map(Vertex::getElement).toList());
        }
    }

    public void removeVertex(Vertex v) {
        for (Edge e : v.getEdges().values())
            removeEdge(e);

        for (Edge e : v.getEdges().values())
            removeEdge(e);

        vertices.remove(v);
    }

    public void removeEdge(Edge e) {
        Vertex[] endpoints = e.getEndpoints();
        endpoints[0].getEdges().remove(endpoints[1]);
        endpoints[1].getEdges().remove(endpoints[0]);
        edges.remove(e);
    }

    public List<List<Vertex>> bfsLevels(Vertex s, int maxLevel) {

        Set<Vertex> visited = new HashSet<>();
        List<List<Vertex>> levels = new ArrayList<>();
        List<Vertex> currentLevel = new ArrayList<>();
        Map<Vertex, Edge> forest = new HashMap<>(); // todo
        currentLevel.add(s);
        visited.add(s);

        while (!currentLevel.isEmpty()) {

            levels.add(currentLevel);
            List<Vertex> nextLevel = new ArrayList<>();

            for (Vertex u : currentLevel) {
                for (Edge e : edges(u)) {
                    Vertex v = opposite(u, e);
                    if (!visited.contains(v)) {
                        visited.add(v);
                        nextLevel.add(v);
                        forest.put(v, e); // todo use this to reconstruct the path
                    }
                }
            }
            currentLevel = nextLevel;

            if (levels.size() == maxLevel) {
                break;
            }
        }

        // todo delete this
        System.out.println("BFS levels: ");
        for (List<Vertex> level : levels) {
            System.out.println(level.stream().map(Vertex::getElement).toList());
        }

        return levels;
    }

    public void BFS(Vertex s, Set<Vertex> known, Map<Vertex, Edge> forest) {

        Queue<Vertex> q = new LinkedList<>();
        known.add(s);
        q.add(s);

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            for (Edge e : this.edges(u)) {
                Vertex v = this.opposite(u, e);
                if (!known.contains(v)) {
                    known.add(v);
                    forest.put(v, e);
                    q.add(v);
                }
            }
        }
    }

    // Vertex Class ---------------------------------------------------------------------------------------------------

    // Edge Class ------------------------------------------------------------------------------------------------------
}