package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * AdjacencyMatrixGraph represents a directed graph using an adjacency matrix. Each vertex is
 * represented by a row/column in the matrix, and edges are represented by non-zero values in the
 * matrix.
 */
public class AdjacencyMatrixGraph implements Graph {

    private int[][] matrix;
    private int size;
    private int capacity;

    private static final int INITIAL_CAPACITY = 8;
    private static final double SHRINK_THRESHOLD = 0.33;

    /**
     * Constructs an empty graph with an initial capacity of vertices.
     */
    public AdjacencyMatrixGraph() {
        this.capacity = INITIAL_CAPACITY;
        matrix = new int[capacity][capacity];
        this.size = 0;
    }

    /**
     * Checks if a vertex index is within valid bounds of the graph.
     *
     * @param vertex the vertex to check.
     * @throws IllegalArgumentException if the vertex is out of bounds.
     */
    private void checkVertexBounds(int vertex) {
        if (vertex < 0 || vertex >= size) {
            throw new IllegalArgumentException("Vertex " + vertex + " is out of bounds");
        }
    }

    /**
     * Adds a vertex to the graph. Expands the capacity if necessary.
     *
     * @param vertex the vertex to be added.
     */
    @Override
    public void addVertex(int vertex) {
        if (vertex >= capacity) {
            expandCapacity();
        }
        if (vertex >= size) {
            size++;
        }
        matrix[vertex][vertex] = 1;
    }

    /**
     * Expands the capacity of the matrix to accommodate more vertices.
     */
    private void expandCapacity() {
        int newCapacity = capacity * 2;
        int[][] newMatrix = new int[newCapacity][newCapacity];

        for (int i = 0; i < capacity; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, capacity);
        }

        matrix = newMatrix;
        capacity = newCapacity;
    }

    /**
     * Shrinks the capacity of the matrix if the number of vertices is below a threshold.
     */
    private void shrinkCapacity() {
        int newCapacity = capacity / 2;
        int[][] newMatrix = new int[newCapacity][newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, newCapacity);
        }

        matrix = newMatrix;
        capacity = newCapacity;
    }

    /**
     * Removes a vertex from the graph, along with its associated edges.
     *
     * @param vertex the vertex to be removed.
     */
    @Override
    public void removeVertex(int vertex) {
        checkVertexBounds(vertex);

        for (int i = 0; i < size; i++) {
            matrix[vertex][i] = 0;
            matrix[i][vertex] = 0;
        }

        if (vertex == size) {
            size--;
        }

        if (size < capacity * SHRINK_THRESHOLD && capacity > INITIAL_CAPACITY) {
            shrinkCapacity();
        }
    }

    /**
     * Adds a directed edge between two vertices.
     *
     * @param fromVertex the source vertex.
     * @param toVertex   the destination vertex.
     */
    @Override
    public void addEdge(int fromVertex, int toVertex) {
        checkVertexBounds(fromVertex);
        checkVertexBounds(toVertex);
        matrix[fromVertex][toVertex] = 2;
    }

    /**
     * Removes a directed edge between two vertices.
     *
     * @param fromVertex the source vertex.
     * @param toVertex   the destination vertex.
     */
    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        checkVertexBounds(fromVertex);
        checkVertexBounds(toVertex);
        matrix[fromVertex][toVertex] = 0;
    }

    /**
     * Retrieves a list of neighboring vertices directly connected to the given vertex by outgoing
     * edges.
     *
     * @param vertex the vertex whose neighbors are to be retrieved.
     * @return a list of neighboring vertices.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        checkVertexBounds(vertex);
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (matrix[vertex][i] == 2) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    /**
     * Reads graph data from a file and adds edges accordingly. The file should contain lines where
     * each line represents an edge between two vertices.
     *
     * @param path the path to the file containing the graph data.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        size = lines.size();

        while (size > capacity) {
            this.expandCapacity();
        }

        for (int i = 0; i < size; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
    }

    /**
     * Performs a topological sort of the graph. If the graph contains a cycle, an exception is
     * thrown.
     *
     * @return a list of vertices in topologically sorted order.
     * @throws IllegalArgumentException if the graph contains a cycle.
     */
    @Override
    public List<Integer> topologicalSort() throws IllegalArgumentException {
        boolean[] visited = new boolean[size];
        boolean[] recursionStack = new boolean[size];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
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
     * Recursive helper method for topological sort.
     *
     * @param v              the current vertex being visited.
     * @param visited        array indicating whether a vertex has been visited.
     * @param recursionStack array indicating vertices in the current recursion stack (for cycle
     *                       detection).
     * @param result         list storing the topologically sorted vertices.
     * @return true if no cycle is found, false otherwise.
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
     * Compares this graph with another object for equality. Two graphs are considered equal if they
     * have the same size and adjacency matrix.
     *
     * @param obj the object to compare this graph against.
     * @return true if the graphs are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdjacencyMatrixGraph other)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (size != other.size) {
            return false;
        }

        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                if (matrix[i][j] != other.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a string representation of the adjacency matrix of the graph.
     *
     * @return a string representing the adjacency matrix.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
