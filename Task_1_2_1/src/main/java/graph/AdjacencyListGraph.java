package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * AdjacencyListGraph represents a directed graph using an adjacency list. Each vertex is mapped to
 * a list of vertices it is connected to by outgoing edges.
 */
public class AdjacencyListGraph implements Graph {

    protected Map<Integer, List<Integer>> adjacencyList;

    /**
     * Constructs an empty graph using an adjacency list.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        adjacencyList.values().forEach(e -> e.remove(Integer.valueOf(vertex)));
        adjacencyList.remove(vertex);
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        addVertex(fromVertex);
        addVertex(toVertex);
        adjacencyList.get(fromVertex).add(toVertex);
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        List<Integer> neighbors = adjacencyList.get(fromVertex);
        if (neighbors != null) {
            neighbors.remove(Integer.valueOf(toVertex));
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length < 2) {
                continue; // Пропускаем некорректные строки
            }
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            addEdge(from, to);
        }
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            int fromVertex = entry.getKey();
            for (int toVertex : entry.getValue()) {
                edges.add(new Edge(fromVertex, toVertex));
            }
        }
        return edges;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }

        Set<Integer> thisVertices = new HashSet<>(this.getVertices());
        Set<Integer> otherVertices = new HashSet<>(other.getVertices());
        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        Set<Edge> thisEdges = this.getEdges();
        Set<Edge> otherEdges = other.getEdges();
        return thisEdges.equals(otherEdges);
    }


    @Override
    public int hashCode() {
        Set<Integer> vertices = new HashSet<>(this.getVertices());
        Set<Edge> edges = this.getEdges();
        return Objects.hash(vertices, edges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> getVertices() {
        Set<Integer> vertices = new HashSet<>(adjacencyList.keySet());
        for (List<Integer> neighbors : adjacencyList.values()) {
            vertices.addAll(neighbors);
        }
        return new ArrayList<>(vertices);
    }
}