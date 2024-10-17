package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
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
            "0 0 0 -1 -1 0\n-1 0 0 0 0 -1\n0 1 -1 0 0 0\n1 -1 0 0 0 0\n0 0 0 0 1 1\n0 0 1 1 0 0\n");
        graph.readFromFile(path);
        assertEquals(List.of(2, 0), graph.getNeighbors(5));
        assertEquals(List.of(1), graph.getNeighbors(3));
    }

    @Test
    void testToString() throws Exception {
        Path path = Path.of("testGraphIncidence.txt");
        String matrix = """
            0 0 0 -1 -1 0
            -1 0 0 0 0 -1
            0 1 -1 0 0 0
            1 -1 0 0 0 0
            0 0 0 0 1 1
            0 0 1 1 0 0
            """;
        Files.writeString(path, matrix);
        graph.readFromFile(path);
        assertEquals(matrix, graph.toString());
    }

    @Test
    void testResizeVertexCapacity() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        int initialVertexCapacity = 16;
        int numberOfVerticesToAdd = 100;

        for (int i = 0; i < numberOfVerticesToAdd; i++) {
            graph.addVertex(i);
        }

        List<Integer> vertices = graph.getVertices();
        assertEquals(numberOfVerticesToAdd, vertices.size(),
            "All vertices should be added to the graph.");

        int expectedVertexCapacity = initialVertexCapacity;
        while (expectedVertexCapacity <= numberOfVerticesToAdd) {
            expectedVertexCapacity *= 2;
        }
        assertEquals(expectedVertexCapacity, graph.getVertexCapacity(),
            "Vertex capacity should have increased appropriately.");

        for (int i = 0; i < numberOfVerticesToAdd; i++) {
            assertTrue(vertices.contains(i), "Graph should contain vertex " + i);
        }
    }

    @Test
    void testResizeEdgeCapacity() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        int initialEdgeCapacity = 16;
        int numberOfEdgesToAdd = 100;

        graph.addVertex(0);
        graph.addVertex(1);

        for (int i = 0; i < numberOfEdgesToAdd; i++) {
            graph.addEdge(0, 1);
        }

        Set<Edge> edges = graph.getEdges();
        assertEquals(1, edges.size(), "Graph should not allow duplicate edges.");

        graph = new IncidenceMatrixGraph();
        for (int i = 0; i < numberOfEdgesToAdd; i++) {
            graph.addVertex(i);
            graph.addVertex(i + 1);
            graph.addEdge(i, i + 1);
        }

        int expectedEdgeCapacity = initialEdgeCapacity;
        while (expectedEdgeCapacity <= numberOfEdgesToAdd) {
            expectedEdgeCapacity *= 2;
        }
        assertEquals(expectedEdgeCapacity, graph.getEdgeCapacity(),
            "Edge capacity should have increased appropriately.");

        edges = graph.getEdges();
        assertEquals(numberOfEdgesToAdd, edges.size(), "All edges should be added to the graph.");

        for (int i = 0; i < numberOfEdgesToAdd; i++) {
            assertTrue(edges.contains(new Edge(i, i + 1)),
                "Graph should contain edge from " + i + " to " + (i + 1));
        }
    }
}
