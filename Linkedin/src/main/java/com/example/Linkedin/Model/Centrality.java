package com.example.Linkedin.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;


public class Centrality {

    Graph graph;
    public Centrality(Graph graph) {
        this.graph = graph;
        graph.identifyComponentsDFS();
    }
    public ArrayList<ArrayList<Node>> degreeCentrality() {
        ArrayList<ArrayList<Node>> degree = new ArrayList<>();

        for(HashSet<Vertex> component : graph.getComponents().values()) {
            ArrayList<Node> centralityValue = new ArrayList<>();

            for (Vertex vertex : component) {
                int incidentNodes = vertex.getEdges().size();
                centralityValue.add(new Node(vertex, incidentNodes));
            }

            ArrayList<Node> results = new ArrayList<>();
            centralityValue.sort(Collections.reverseOrder());
            Iterator<Node> it = centralityValue.iterator();

            while (it.hasNext() && results.size() < 5) {
                results.add(it.next());
            }

            degree.add(results);
        }
        return degree;
    }

}
