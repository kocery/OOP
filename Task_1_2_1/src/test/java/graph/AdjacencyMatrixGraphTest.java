package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex(0);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.removeVertex(1);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testAddEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testReadFromFile() throws Exception {
        Graph graph = new AdjacencyMatrixGraph();
        Path path = Path.of("testGraphIncidence.txt");
        Files.writeString(path, """
            1 1 0 0 1 0
            1 0 1 0 1 0
            0 1 0 1 0 0
            0 0 1 0 1 1
            1 1 0 1 0 0
            0 0 0 1 0 0""");
        graph.readFromFile(path);
        assertEquals(List.of(3), graph.getNeighbors(5));
        assertEquals(List.of(2, 4, 5), graph.getNeighbors(3));
    }
}