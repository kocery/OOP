package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * IncidenceMatrixGraph represents a directed graph using an incidence matrix. Each row corresponds
 * to a vertex, and each column corresponds to an edge. The matrix contains values that describe the
 * relationship between vertices and edges. Value 1 indicates the starting vertex of an edge, and
 * value 2 indicates the ending vertex.
 */
public class IncidenceMatrixGraph implements Graph {

    private int[][] matrix;
    private int vertexCount;
    private int edgeCount;

    /**
     * Constructs a graph with the specified number of vertices and edges.
     *
     * @param vertices the number of vertices in the graph.
     * @param edges    the number of edges in the graph.
     */
    public IncidenceMatrixGraph(int vertices, int edges) {
        this.vertexCount = vertices;
        this.edgeCount = edges;
        matrix = new int[vertices][edges];
    }

    /**
     * Adds a new vertex to the graph. If the specified vertex index is greater than or equal to the
     * current number of vertices, the vertex count is increased and the matrix is resized
     * accordingly.
     *
     * @param vertex the index of the vertex to be added.
     */
    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCount) {
            vertexCount++;
            int[][] newMatrix = new int[vertexCount][edgeCount];
            for (int i = 0; i < matrix.length; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
            }
            matrix = newMatrix;
        }
    }

    /**
     * Removes the specified vertex from the graph and adjusts the incidence matrix.
     *
     * @param vertex the index of the vertex to be removed.
     */
    @Override
    public void removeVertex(int vertex) {
        if (vertex < vertexCount) {
            vertexCount--;
            int[][] newMatrix = new int[vertexCount][edgeCount];
            for (int i = 0, newI = 0; i < matrix.length; i++) {
                if (i == vertex) {
                    continue;
                }
                System.arraycopy(matrix[i], 0, newMatrix[newI], 0, edgeCount);
                newI++;
            }
            matrix = newMatrix;
        }
    }

    /**
     * Adds an edge between two vertices. The edge is represented in the incidence matrix with a
     * value of 1 for the starting vertex and 2 for the ending vertex.
     *
     * @param fromVertex the starting vertex of the edge.
     * @param toVertex   the ending vertex of the edge.
     */
    @Override
    public void addEdge(int fromVertex, int toVertex) {
        for (int edge = 0; edge < edgeCount; edge++) {
            boolean isEmpty = true;
            for (int v = 0; v < vertexCount; v++) {
                if (matrix[v][edge] != 0) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                matrix[fromVertex][edge] = 1;
                matrix[toVertex][edge] = 2;
                return;
            }
        }
    }

    /**
     * Removes the edge between two vertices by setting the corresponding values in the incidence
     * matrix to 0.
     *
     * @param fromVertex the starting vertex of the edge.
     * @param toVertex   the ending vertex of the edge.
     */
    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        for (int edge = 0; edge < edgeCount; edge++) {
            if (matrix[fromVertex][edge] == 1 && matrix[toVertex][edge] == 2) {
                matrix[fromVertex][edge] = 0;
                matrix[toVertex][edge] = 0;
                return;
            }
        }
    }

    /**
     * Returns a list of neighbors for the specified vertex. A neighbor is defined as a vertex
     * connected to the given vertex by an edge.
     *
     * @param vertex the vertex whose neighbors are to be retrieved.
     * @return a list of neighbors of the specified vertex.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int edge = 0; edge < edgeCount; edge++) {
            if (matrix[vertex][edge] == 1) {
                for (int v = 0; v < vertexCount; v++) {
                    if (matrix[v][edge] == 2) {
                        neighbors.add(v);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Reads the incidence matrix from a file. The first line of the file contains the number of
     * vertices and edges. The remaining lines contain the matrix values.
     *
     * @param path the path to the file containing the graph data.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        String[] firstLine = lines.getFirst().split(" ");
        vertexCount = lines.size();
        edgeCount = firstLine.length;

        matrix = new int[vertexCount][edgeCount];

        for (int i = 0; i < vertexCount; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < edgeCount; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
    }

    /**
     * Performs a topological sort of the graph. If the graph contains a cycle, an
     * IllegalArgumentException is thrown.
     *
     * @return a list of vertices in topologically sorted order.
     * @throws IllegalArgumentException if the graph contains a cycle.
     */
    @Override
    public List<Integer> topologicalSort() throws IllegalArgumentException {
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

        return result;
    }

    /**
     * Utility method for performing topological sort recursively.
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
        result.addFirst(v);
        return true;
    }

    /**
     * Compares this graph to the specified object. The result is true if and only if the argument
     * is an IncidenceMatrixGraph with the same number of vertices and edges, and the same incidence
     * matrix.
     *
     * @param obj the object to compare this graph against.
     * @return true if the given object represents an IncidenceMatrixGraph equivalent to this graph,
     * false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IncidenceMatrixGraph other)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (vertexCount != other.vertexCount || edgeCount != other.edgeCount) {
            return false;
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                if (matrix[i][j] != other.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a string representation of the graph's incidence matrix.
     *
     * @return a string representation of the incidence matrix.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

