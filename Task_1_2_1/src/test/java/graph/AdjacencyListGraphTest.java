package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AdjacencyListGraphTest {

    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
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
        Path path = Path.of("testGraph.txt");
        Files.writeString(path, "0 1\n1 2\n");
        graph.readFromFile(path);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    void testToString() {
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        String ans = """
            0 -> []
            1 -> []
            2 -> [3]
            3 -> [1]
            4 -> [0, 1]
            5 -> [2, 0]
            """;
        assertEquals(ans, graph.toString());
    }
}

