package graph;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Graph {

    void addVertex(int vertex);

    void removeVertex(int vertex);

    void addEdge(int fromVertex, int toVertex);

    void removeEdge(int fromVertex, int toVertex);

    List<Integer> getNeighbors(int vertex);

    void readFromFile(Path path) throws IOException;

    List<Integer> topologicalSort() throws IllegalArgumentException;

    String toString();
}