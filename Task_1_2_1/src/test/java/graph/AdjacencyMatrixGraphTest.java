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

    @Test
    void testToString() throws Exception {
        Path path = Path.of("testGraphIncidence.txt");
        String matrix = """
            1 1 0 0 1 0
            1 0 1 0 1 0
            0 1 0 1 0 0
            0 0 1 0 1 1
            1 1 0 1 0 0
            0 0 0 1 0 0
            """;
        Files.writeString(path, matrix);
        graph.readFromFile(path);
        System.out.println(graph);
        assertEquals(matrix, graph.toString());
    }

    @Test
    void testIncreaseCapacity() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        int initialCapacity = 16;
        int numberOfVerticesToAdd = 100;

        for (int i = 0; i < numberOfVerticesToAdd; i++) {
            graph.addVertex(i);
        }

        List<Integer> vertices = graph.getVertices();
        assertEquals(numberOfVerticesToAdd, vertices.size(),
            "All vertices should be added to the graph.");

        for (int i = 0; i < numberOfVerticesToAdd; i++) {
            assertTrue(vertices.contains(i), "Graph should contain vertex " + i);
        }

        for (int i = 0; i < numberOfVerticesToAdd - 1; i++) {
            graph.addEdge(i, i + 1);
        }

        for (int i = 0; i < numberOfVerticesToAdd - 1; i++) {
            List<Integer> neighbors = graph.getNeighbors(i);
            assertEquals(1, neighbors.size(), "Vertex " + i + " should have one neighbor.");
            assertEquals(i + 1, neighbors.get(0),
                "Vertex " + i + " should be connected to vertex " + (i + 1));
        }

        int expectedCapacity = initialCapacity;
        while (expectedCapacity < numberOfVerticesToAdd) {
            expectedCapacity *= 2;
        }
        assertEquals(expectedCapacity, graph.getCapacity(),
            "Graph capacity should have increased appropriately.");
    }

    @Test
    void testDecreaseCapacity() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        int initialCapacity = 16;
        int numberOfVerticesToAdd = 100;

        for (int i = 0; i < numberOfVerticesToAdd; i++) {
            graph.addVertex(i);
        }

        for (int i = 0; i < numberOfVerticesToAdd - 1; i++) {
            graph.addEdge(i, i + 1);
        }

        int expectedCapacity = initialCapacity;
        while (expectedCapacity < numberOfVerticesToAdd) {
            expectedCapacity *= 2;
        }
        assertEquals(expectedCapacity, graph.getCapacity(),
            "Capacity should have increased appropriately.");

        int numberOfVerticesToRemove = (int) (numberOfVerticesToAdd - expectedCapacity / 4 + 1);
        for (int i = 0; i < numberOfVerticesToRemove; i++) {
            graph.removeVertex(i);
        }

        int expectedNewCapacity = expectedCapacity;
        while (graph.getVertexCount() < expectedNewCapacity / 4
            && expectedNewCapacity > initialCapacity) {
            expectedNewCapacity /= 2;
        }

        assertEquals(expectedNewCapacity, graph.getCapacity(),
            "Capacity should have decreased appropriately.");

        List<Integer> vertices = graph.getVertices();
        assertEquals(numberOfVerticesToAdd - numberOfVerticesToRemove, vertices.size(),
            "Graph should have correct number of vertices after removal.");

        for (int i = numberOfVerticesToRemove; i < numberOfVerticesToAdd; i++) {
            assertTrue(vertices.contains(i), "Graph should still contain vertex " + i);
        }

        for (int i = numberOfVerticesToRemove; i < numberOfVerticesToAdd - 1; i++) {
            List<Integer> neighbors = graph.getNeighbors(i);
            assertEquals(1, neighbors.size(), "Vertex " + i + " should have one neighbor.");
            assertEquals(i + 1, neighbors.get(0),
                "Vertex " + i + " should be connected to vertex " + (i + 1));
        }

        List<Integer> neighbors = graph.getNeighbors(numberOfVerticesToAdd - 1);
        assertEquals(0, neighbors.size(), "Last vertex should have no neighbors.");
    }
}