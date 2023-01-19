package com.example.Linkedin.Model;

import java.util.*;


public class Centrality {

    Graph graph;
    public HashMap<String, Double> centralizationDegreeComponents;

    public Centrality(Graph graph) {
        this.graph = graph;
        graph.identifyComponentsDFS();
    }
    public ArrayList<ArrayList<Node>> degreeCentrality() {

        ArrayList<ArrayList<Node>> degree = new ArrayList<>();
        centralizationDegreeComponents = new HashMap<>();

        for(String componentKey : graph.getComponents().keySet()) {
            HashSet<Vertex> component = graph.getComponents().get(componentKey);
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

            int maximumDegree = (int)Collections.max(centralityValue).value;
            double cDegree = 0.0;
            for (Node node : centralityValue) {
                cDegree += (maximumDegree - node.value);
            }
            cDegree /= Math.pow(component.size(), 2) - 3 * component.size() + 2;

            centralizationDegreeComponents.put(componentKey, cDegree);
            degree.add(results);
        }

        return degree;
    }

    public ArrayList<ArrayList<Node>> closenessCentrality() {

        ArrayList<ArrayList<Node>> closeness = new ArrayList<>();

        for(HashSet<Vertex> component : graph.getComponents().values()) {
            ArrayList<Node> centralityValue = new ArrayList<>();

            for (Vertex source : component) {
                HashMap<Vertex, Integer> distance = new HashMap<>();
                Queue<Vertex> queue = new LinkedList<>();
                distance.put(source, 0);
                queue.add(source);

                while (!queue.isEmpty()) {
                    Vertex u = queue.poll();
                    for (Vertex v : u.getEdges().keySet()) {
                        if (!distance.containsKey(v) || distance.get(u)+1 < distance.get(v)) {
                            distance.put(v, distance.get(u) + 1);
                            queue.add(v);
                        }
                    }
                }

                int totalDistance = 0;
                for (Integer nodeDistance : distance.values()) {
                    totalDistance += nodeDistance;
                }

                double CValue = Math.pow(component.size()-1, 2) / graph.numVertices();
                CValue *= 1.0/totalDistance;

                centralityValue.add(new Node(source, CValue));
            }

            ArrayList<Node> results = new ArrayList<>();
            Collections.sort(centralityValue);
            Iterator<Node> it = centralityValue.iterator();

            while (it.hasNext() && results.size() < 5) {
                results.add(it.next());
            }

            closeness.add(results);
        }
        return closeness;

    }

    public ArrayList<ArrayList<Node>> betweennessCentrality() {
        ArrayList<ArrayList<Node>> betweenness = new ArrayList<>();

        for (HashSet<Vertex> component : graph.getComponents().values()) {

            // stores the betweenness Centrality value for each node
            HashMap<Vertex, Double> centrality = new HashMap<>();

            for (Vertex source : component) {

                Stack<Vertex> stack = new Stack<>();
                Queue<Vertex> queue = new LinkedList<>();
                HashMap<Vertex, ArrayList<Vertex>> precedingNode = new HashMap<>();
                HashMap<Vertex, Integer> distance = new HashMap<>();
                HashMap<Vertex, Integer> paths = new HashMap<>();

                queue.add(source);
                paths.put(source, 1);
                distance.put(source, 0);

                while (!queue.isEmpty()) {
                    Vertex current = queue.poll();

                    // used for the dependency accumulation algorithm later
                    stack.push(current);

                    for (Vertex node : current.getEdges().keySet()) {

                        // node is founded for the first time
                        if (!distance.containsKey(node)) {
                            queue.add(node);
                            distance.put(node, distance.get(current) + 1);
                            paths.put(node, paths.get(current));
                            ArrayList<Vertex> parent = new ArrayList<>();
                            parent.add(current);
                            precedingNode.put(node, parent);

                            // found another SP for existing node
                        } else if (distance.get(node) == distance.get(current) + 1) {
                            paths.put(node, (paths.get(node) + paths.get(current)));
                            ArrayList<Vertex> precede = precedingNode.get(node);
                            precede.add(current);
                            precedingNode.put(node, precede);
                        }
                    }
                }
                /*
                 * Runs Brandes' dependency accumulation algorithm to compute
                 * the betweenness centrality for each node.
                 */
                HashMap<Vertex, Double> dependency = new HashMap<>();
                for (Vertex node : component) {
                    dependency.put(node, 0.0);
                }
                while (!stack.isEmpty()) {
                    Vertex current = stack.pop();
                    if (current != source) {
                        for (Vertex node : precedingNode.get(current)) {
                            double result = ((double) paths.get(node)
                                    / paths.get(current)) * (1 + dependency.get(current));
                            dependency.put(node, dependency.get(node) + result);
                        }
                        if (!centrality.containsKey(current)) {
                            // divided by 2 due to the graph's undirected nature
                            centrality.put(current, dependency.get(current) / 2);
                        } else {
                            centrality.put(current, centrality.get(current)
                                    + dependency.get(current) / 2);
                        }
                    }
                }
            }
            // stores the centrality value for each node as a Node object
            ArrayList<Node> centralityValue = new ArrayList<>();
            for (Vertex node : centrality.keySet()) {
                centralityValue.add(new Node(node, centrality.get(node)));
            }
            // sorts the nodes according to its closeness centrality
            ArrayList<Node> results = new ArrayList<>();
            Collections.sort(centralityValue, Collections.reverseOrder());
            Iterator<Node> it = centralityValue.iterator();
            while (it.hasNext() && results.size() < 5) {
                results.add(it.next());
            }
            betweenness.add(results);
        }

        return betweenness;
    }
}
