package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex(3);
        assertEquals(0, graph.getNeighbors(3).size());
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
        Path path = Path.of("testGraphIncidence.txt");
        Files.writeString(path,
            "0 0 0 2 2 0\n2 0 0 0 0 2\n0 1 2 0 0 0\n1 2 0 0 0 0\n0 0 0 0 1 1\n0 0 1 1 0 0\n");
        graph.readFromFile(path);
        assertEquals(List.of(2, 0), graph.getNeighbors(5));
        assertEquals(List.of(1), graph.getNeighbors(3));
    }
}