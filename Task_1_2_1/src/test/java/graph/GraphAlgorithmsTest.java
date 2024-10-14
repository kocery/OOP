package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class GraphAlgorithmsTest {

    @Test
    void AdjacencyListGraphTopologicalSort() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        List<Integer> sorted = GraphAlgorithms.topologicalSort(graph);
        assertEquals(List.of(5, 4, 2, 3, 1, 0), sorted);
    }

    @Test
    void IncidenceMatrixGraphTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        List<Integer> sorted = GraphAlgorithms.topologicalSort(graph);
        assertEquals(List.of(5, 4, 2, 3, 1, 0), sorted);
    }

    @Test
    void AdjacencyMatrixGraphTopologicalSort() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        List<Integer> sorted = GraphAlgorithms.topologicalSort(graph);
        assertEquals(List.of(5, 4, 2, 3, 1, 0), sorted);
    }

    @Test
    void testTopologicalSortWithCycle() {
        // Create a graph with a cycle: 1 -> 2 -> 3 -> 1
        Graph graph = new AdjacencyListGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // This edge creates a cycle

        // Assert that an IllegalArgumentException is thrown when attempting topological sort
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> GraphAlgorithms.topologicalSort(graph));

        String expectedMessage = "Graph has a cycle, topological sort not possible.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
