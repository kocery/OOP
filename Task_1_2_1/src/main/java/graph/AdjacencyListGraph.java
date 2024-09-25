package graph;

import static java.util.Collections.reverse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdjacencyListGraph represents a directed graph using an adjacency list. Each vertex is mapped to
 * a list of vertices it is connected to by outgoing edges.
 */
public class AdjacencyListGraph implements Graph {

    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    /**
     * Adds a vertex to the graph. If the vertex already exists, no changes are made.
     *
     * @param vertex the vertex to be added.
     */
    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Removes a vertex and all edges connected to it from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    @Override
    public void removeVertex(int vertex) {
        adjacencyList.values().forEach(e -> e.remove(Integer.valueOf(vertex)));
        adjacencyList.remove(vertex);
    }

    /**
     * Adds a directed edge from one vertex to another.
     *
     * @param fromVertex the starting vertex of the edge.
     * @param toVertex   the ending vertex of the edge.
     */
    @Override
    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList.putIfAbsent(fromVertex, new ArrayList<>());
        adjacencyList.get(fromVertex).add(toVertex);
    }

    /**
     * Removes a directed edge from one vertex to another.
     *
     * @param fromVertex the starting vertex of the edge.
     * @param toVertex   the ending vertex of the edge.
     */
    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        List<Integer> neighbors = adjacencyList.get(fromVertex);
        if (neighbors != null) {
            neighbors.remove(Integer.valueOf(toVertex));
        }
    }

    /**
     * Returns a list of neighbors (vertices directly reachable by an outgoing edge) of the given
     * vertex.
     *
     * @param vertex the vertex whose neighbors are to be retrieved.
     * @return a list of neighboring vertices.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Reads graph data from a file. Each line in the file should represent an edge, with two
     * integers (fromVertex toVertex) separated by a space.
     *
     * @param path the path to the file containing the graph data.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(" ");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            addEdge(from, to);
        }
    }

    /**
     * Performs a topological sort on the graph. If the graph contains a cycle, an
     * IllegalArgumentException is thrown.
     *
     * @return a list of vertices in topologically sorted order.
     * @throws IllegalArgumentException if the graph contains a cycle.
     */
    @Override
    public List<Integer> topologicalSort() throws IllegalArgumentException {
        int vertexCount = adjacencyList.size();
        boolean[] visited = new boolean[vertexCount];
        boolean[] recursionStack = new boolean[vertexCount];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) {
                if (!topologicalSortUtil(i, visited, recursionStack, result)) {
                    throw new IllegalArgumentException(
                        "Graph has a cycle, topological sort not possible.");
                }
            }
        }

        reverse(result);
        return result;
    }

    /**
     * Utility method for performing a recursive topological sort.
     *
     * @param v              the current vertex being visited.
     * @param visited        array indicating whether a vertex has been visited.
     * @param recursionStack array indicating vertices in the current recursion stack (used to
     *                       detect cycles).
     * @param result         list storing the topologically sorted vertices.
     * @return true if topological sort is possible, false if a cycle is detected.
     */
    private boolean topologicalSortUtil(int v, boolean[] visited, boolean[] recursionStack,
        List<Integer> result) {
        visited[v] = true;
        recursionStack[v] = true;

        for (int neighbor : getNeighbors(v)) {
            if (!visited[neighbor]) {
                if (!topologicalSortUtil(neighbor, visited, recursionStack, result)) {
                    return false;
                }
            } else if (recursionStack[neighbor]) {
                return false;
            }
        }

        recursionStack[v] = false;
        result.add(v);
        return true;
    }

    /**
     * Compares this graph to the specified object. The result is true if and only if the argument
     * is an AdjacencyListGraph with the same adjacency list.
     *
     * @param obj the object to compare this graph against.
     * @return true if the given object represents an AdjacencyListGraph equivalent to this graph,
     * false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdjacencyListGraph other)) {
            return false;
        }
        return adjacencyList.equals(other.adjacencyList);
    }

    /**
     * Returns a string representation of the graph's adjacency list.
     *
     * @return a string representation of the adjacency list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}

