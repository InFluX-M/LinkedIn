package com.example.Linkedin.Model;

public class Node implements Comparable<Node> {
    public Vertex vertex;
    public Integer value;

    public Node(Vertex vertex, Integer value) {
        this.vertex = vertex;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(value, node.value);
    }
}