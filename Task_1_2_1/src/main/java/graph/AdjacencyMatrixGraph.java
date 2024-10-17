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
 * AdjacencyMatrixGraph represents a directed graph using an adjacency matrix. Each vertex is
 * represented by a row/column in the matrix, and edges are represented by non-zero values in the
 * matrix.
 */
public class AdjacencyMatrixGraph implements Graph {

    private boolean[][] adjacencyMatrix;
    private final Map<Integer, Integer> vertexIndexMap; // Vertex label to index
    private final Map<Integer, Integer> indexVertexMap; // Index to vertex label
    private int vertexCount;
    private int capacity;

    /**
     * Constructs an empty graph with an initial capacity for vertices. The initial capacity is set
     * to 16 vertices.
     */
    public AdjacencyMatrixGraph() {
        this.capacity = 16;
        this.adjacencyMatrix = new boolean[capacity][capacity];
        this.vertexIndexMap = new HashMap<>();
        this.indexVertexMap = new HashMap<>();
        this.vertexCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            return;
        }

        if (vertexCount == capacity) {
            increaseCapacity();
        }

        vertexIndexMap.put(vertex, vertexCount);
        indexVertexMap.put(vertexCount, vertex);
        vertexCount++;
    }

    /**
     * Doubles the capacity of the adjacency matrix to accommodate more vertices. This method is
     * called when the number of vertices reaches the current capacity.
     */
    private void increaseCapacity() {
        capacity *= 2;
        boolean[][] newMatrix = new boolean[capacity][capacity];

        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, vertexCount);
        }

        adjacencyMatrix = newMatrix;
    }

    /**
     * Returns vertexCount of the graph.
     *
     * @return vertexCount of the graph.
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Halves the capacity of the adjacency matrix to save memory when the number of vertices
     * decreases significantly. This method is called when the number of vertices is less than a
     * quarter of the current capacity.
     */
    private void decreaseCapacity() {
        capacity /= 2;
        boolean[][] newMatrix = new boolean[capacity][capacity];

        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, vertexCount);
        }

        adjacencyMatrix = newMatrix;
    }

    @Override
    public void removeVertex(int vertex) {
        Integer idx = vertexIndexMap.get(vertex);
        if (idx == null) {
            return;
        }

        vertexIndexMap.remove(vertex);
        indexVertexMap.remove(idx);

        for (Map.Entry<Integer, Integer> entry : vertexIndexMap.entrySet()) {
            int v = entry.getKey();
            int i = entry.getValue();
            if (i > idx) {
                vertexIndexMap.put(v, i - 1);
                indexVertexMap.put(i - 1, v);
            }
        }
        indexVertexMap.remove(vertexCount - 1);

        vertexCount--;

        boolean[][] newMatrix = new boolean[capacity][capacity];
        for (int i = 0; i < vertexCount + 1; i++) {
            if (i == idx) {
                continue;
            }
            int ni = i < idx ? i : i - 1;
            for (int j = 0; j < vertexCount + 1; j++) {
                if (j == idx) {
                    continue;
                }
                int nj = j < idx ? j : j - 1;
                newMatrix[ni][nj] = adjacencyMatrix[i][j];
            }
        }
        adjacencyMatrix = newMatrix;

        if (vertexCount > 16 && vertexCount < capacity / 4) {
            decreaseCapacity();
        }
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        if (!vertexIndexMap.containsKey(fromVertex)) {
            addVertex(fromVertex);
        }
        if (!vertexIndexMap.containsKey(toVertex)) {
            addVertex(toVertex);
        }
        int fromIdx = vertexIndexMap.get(fromVertex);
        int toIdx = vertexIndexMap.get(toVertex);
        adjacencyMatrix[fromIdx][toIdx] = true;
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        Integer fromIdx = vertexIndexMap.get(fromVertex);
        Integer toIdx = vertexIndexMap.get(toVertex);
        if (fromIdx == null || toIdx == null) {
            return;
        }
        adjacencyMatrix[fromIdx][toIdx] = false;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        Integer idx = vertexIndexMap.get(vertex);
        if (idx == null) {
            throw new IllegalArgumentException(
                "Vertex " + vertex + " does not exist in the graph.");
        }
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[idx][i]) {
                neighbors.add(indexVertexMap.get(i));
            }
        }
        return neighbors;
    }


    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexIndexMap.keySet());
    }

    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        int size = lines.size();

        this.adjacencyMatrix = new boolean[size][size];
        this.vertexIndexMap.clear();
        this.indexVertexMap.clear();
        this.vertexCount = 0;
        this.capacity = size;

        for (int i = 0; i < size; i++) {
            addVertex(i);
        }

        for (int i = 0; i < size; i++) {
            String[] tokens = lines.get(i).trim().split("\\s+");
            if (tokens.length != size) {
                throw new IOException("Invalid adjacency matrix format at line " + (i + 1));
            }
            for (int j = 0; j < size; j++) {
                int value = Integer.parseInt(tokens[j]);
                adjacencyMatrix[i][j] = (value != 0);
            }
        }
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < vertexCount; i++) {
            int fromVertex = indexVertexMap.get(i);
            for (int j = 0; j < vertexCount; j++) {
                if (adjacencyMatrix[i][j]) {
                    int toVertex = indexVertexMap.get(j);
                    edges.add(new Edge(fromVertex, toVertex));
                }
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
        int size = vertexCount;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean hasEdge = adjacencyMatrix[i][j];
                sb.append(hasEdge ? "1" : "0");
                if (j < size - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns capacity of the graph.
     *
     * @return capacity of the graph.
     */
    public int getCapacity() {
        return capacity;
    }
}