package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * {@code IncidenceMatrixGraph} represents a directed graph using an incidence matrix. The graph
 * allows adding and removing vertices and edges, as well as retrieving neighbors for a given
 * vertex.
 */
public class IncidenceMatrixGraph implements Graph {

    private int[][] incidenceMatrix;
    private boolean[] vertexExists;
    private int vertexCapacity;
    private int edgeCapacity;
    private int vertexCount;
    private int edgeCount;
    private int maxVertexIndex;

    /**
     * Constructs an empty graph using an incidence matrix.
     */
    public IncidenceMatrixGraph() {
        this.vertexCapacity = 16;
        this.edgeCapacity = 16;
        this.incidenceMatrix = new int[vertexCapacity][edgeCapacity];
        this.vertexExists = new boolean[vertexCapacity];
        this.vertexCount = 0;
        this.edgeCount = 0;
        this.maxVertexIndex = -1;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCapacity) {
            resizeVertexCapacity(vertex);
        }
        if (!vertexExists[vertex]) {
            vertexExists[vertex] = true;
            vertexCount++;
            maxVertexIndex = Math.max(maxVertexIndex, vertex);
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCapacity || !vertexExists[vertex]) {
            return;
        }
        vertexExists[vertex] = false;
        vertexCount--;

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] != 0) {
                for (int v = 0; v < vertexCapacity; v++) {
                    incidenceMatrix[v][i] = 0;
                }
            }
        }

        if (vertex == maxVertexIndex) {
            maxVertexIndex = -1;
            for (int i = vertexCapacity - 1; i >= 0; i--) {
                if (vertexExists[i]) {
                    maxVertexIndex = i;
                    break;
                }
            }
        }
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        // Ensure both vertices exist
        addVertex(fromVertex);
        addVertex(toVertex);

        if (edgeCount == edgeCapacity) {
            resizeEdgeCapacity();
        }

        incidenceMatrix[fromVertex][edgeCount] = 1;   // Edge starts from fromVertex
        incidenceMatrix[toVertex][edgeCount] = -1;    // Edge ends at toVertex
        edgeCount++;
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        if (!vertexExists(fromVertex) || !vertexExists(toVertex)) {
            return;
        }

        // Find the edge index
        int edgeIndex = -1;
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[fromVertex][i] == 1 && incidenceMatrix[toVertex][i] == -1) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex == -1) {
            return;
        }

        // Remove edge by shifting edges in the incidence matrix
        for (int v = 0; v < vertexCapacity; v++) {
            System.arraycopy(incidenceMatrix[v], edgeIndex + 1, incidenceMatrix[v], edgeIndex,
                edgeCount - edgeIndex - 1);
            incidenceMatrix[v][edgeCount - 1] = 0; // Clear the last edge slot
        }
        edgeCount--;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (!vertexExists(vertex)) {
            throw new IllegalArgumentException(
                "Vertex " + vertex + " does not exist in the graph.");
        }
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] == 1) {
                for (int v = 0; v < vertexCapacity; v++) {
                    if (!vertexExists[v]) {
                        continue;
                    }
                    if (incidenceMatrix[v][i] == -1) {
                        neighbors.add(v);
                        break;
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public List<Integer> getVertices() {
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i <= maxVertexIndex; i++) {
            if (vertexExists[i]) {
                vertices.add(i);
            }
        }
        return vertices;
    }

    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        int vertexCountInFile = lines.size();
        int edgeCountInFile = lines.getFirst().trim().split("\\s+").length;

        // Reset graph data
        this.vertexCapacity = Math.max(vertexCapacity, vertexCountInFile);
        this.edgeCapacity = Math.max(edgeCapacity, edgeCountInFile);
        this.incidenceMatrix = new int[vertexCapacity][edgeCapacity];
        this.vertexExists = new boolean[vertexCapacity];
        this.vertexCount = vertexCountInFile;
        this.edgeCount = edgeCountInFile;
        this.maxVertexIndex = vertexCountInFile - 1;

        // Initialize vertex existence
        for (int i = 0; i < vertexCountInFile; i++) {
            vertexExists[i] = true;
        }

        // Read the incidence matrix from the file
        for (int i = 0; i < vertexCountInFile; i++) {
            String[] tokens = lines.get(i).trim().split("\\s+");
            if (tokens.length != edgeCountInFile) {
                throw new IOException("Invalid incidence matrix format at line " + (i + 1));
            }
            for (int j = 0; j < edgeCountInFile; j++) {
                incidenceMatrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int edgeCount = this.edgeCount;

        for (int i = 0; i < vertexCapacity; i++) {
            if (!vertexExists[i]) {
                continue;
            }
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]);
                if (j < edgeCount - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edgeSet = new HashSet<>();
        for (int i = 0; i < edgeCount; i++) {
            int from = -1;
            int to = -1;
            for (int v = 0; v < vertexCapacity; v++) {
                if (incidenceMatrix[v][i] == 1) {
                    from = v;
                } else if (incidenceMatrix[v][i] == -1) {
                    to = v;
                }
                if (from != -1 && to != -1) {
                    break;
                }
            }
            if (from != -1 && to != -1) {
                edgeSet.add(new Edge(from, to));
            }
        }
        return edgeSet;
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
        Set<Integer> verticesSet = new HashSet<>(this.getVertices());
        Set<Edge> edgesSet = this.getEdges();
        return Objects.hash(verticesSet, edgesSet);
    }

    /**
     * Checks if a vertex exists in the graph.
     *
     * @param vertex the vertex to check
     * @return {@code true} if the vertex exists; {@code false} otherwise
     */
    private boolean vertexExists(int vertex) {
        return vertex >= 0 && vertex < vertexCapacity && vertexExists[vertex];
    }

    /**
     * Resizes the vertex capacity to accommodate new vertices.
     *
     * @param vertex the vertex index that exceeds the current capacity
     */
    private void resizeVertexCapacity(int vertex) {
        while (vertex >= vertexCapacity) {
            vertexCapacity *= 2;
        }
        int[][] newIncidenceMatrix = new int[vertexCapacity][edgeCapacity];
        for (int i = 0; i < incidenceMatrix.length; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newIncidenceMatrix[i], 0,
                incidenceMatrix[i].length);
        }
        incidenceMatrix = newIncidenceMatrix;

        boolean[] newVertexExists = new boolean[vertexCapacity];
        System.arraycopy(vertexExists, 0, newVertexExists, 0, vertexExists.length);
        vertexExists = newVertexExists;
    }

    /**
     * Resizes the edge capacity to accommodate new edges.
     */
    private void resizeEdgeCapacity() {
        edgeCapacity *= 2;
        for (int i = 0; i < vertexCapacity; i++) {
            incidenceMatrix[i] = Arrays.copyOf(incidenceMatrix[i], edgeCapacity);
        }
    }

    /**
     * Returns vertexCapacity.
     *
     * @return vertexCapacity.
     */
    public int getVertexCapacity() {
        return vertexCapacity;
    }

    /**
     * Returns edgeCapacity.
     *
     * @return edgeCapacity.
     */
    public int getEdgeCapacity() {
        return edgeCapacity;
    }
}