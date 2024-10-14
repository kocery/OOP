package graph;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * The Graph interface represents an unweighted directed graph data structure.
 * It provides methods for adding and removing vertices and edges, retrieving neighbors of a vertex,
 * reading a graph from a file, and getting a list of all vertices.
 */
public interface Graph {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to be added.
     */
    void addVertex(int vertex);

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    void removeVertex(int vertex);

    /**
     * Adds an edge from one vertex to another.
     *
     * @param fromVertex the vertex where the edge starts.
     * @param toVertex   the vertex where the edge ends.
     */
    void addEdge(int fromVertex, int toVertex);

    /**
     * Removes an edge from one vertex to another.
     *
     * @param fromVertex the vertex where the edge starts.
     * @param toVertex   the vertex where the edge ends.
     */
    void removeEdge(int fromVertex, int toVertex);

    /**
     * Returns a list of all neighbors of a given vertex.
     *
     * @param vertex the vertex whose neighbors are to be returned.
     * @return a list of neighbors of the given vertex.
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Returns a list of all vertices in the graph.
     *
     * @return a list of all vertices in the graph.
     */
    List<Integer> getVertices();

    /**
     * Reads the graph structure from a file.
     *
     * @param path the path to the file containing the graph data.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    void readFromFile(Path path) throws IOException;

    /**
     * Returns the set of edges in the graph.
     *
     * @return a set of edges in the graph.
     */
    Set<Edge> getEdges();

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph.
     */
    @Override
    String toString();

    /**
     * Compares the specified object with this graph for equality.
     * Returns {@code true} if and only if the specified object is also a graph,
     * both graphs have the same set of vertices and the same set of edges.
     *
     * @param obj the object to be compared for equality with this graph.
     * @return {@code true} if the specified object is equal to this graph.
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this graph.
     * The hash code is computed based on the sets of vertices and edges.
     *
     * @return the hash code value for this graph.
     */
    @Override
    int hashCode();
}
