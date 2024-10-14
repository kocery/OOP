package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class GraphEqualsTest {

    @Test
    void testEqualsSameTypeGraphsEqual() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyMatrixGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);

        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        assertEquals(graph1, graph2, "Graphs with the same vertices and edges should be equal.");
    }

    @Test
    void testEqualsSameTypeGraphsNotEqual() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyMatrixGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);

        graph2.addEdge(1, 2);
        // graph2.addEdge(2, 3); // Оставляем это ребро не добавленным

        assertNotEquals(graph1, graph2, "Graphs with different edges should not be equal.");
    }

    @Test
    void testEqualsDifferentTypeGraphsEqual() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyListGraph();
        Graph graph3 = new IncidenceMatrixGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);

        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        graph3.addEdge(1, 2);
        graph3.addEdge(2, 3);

        assertEquals(graph1, graph2,
            "Graphs of different types with the same vertices and edges should be equal.");
        assertEquals(graph2, graph3,
            "Graphs of different types with the same vertices and edges should be equal.");
        assertEquals(graph1, graph3,
            "Graphs of different types with the same vertices and edges should be equal.");
    }

    @Test
    void testEqualsDifferentTypeGraphsNotEqual() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyListGraph();
        Graph graph3 = new IncidenceMatrixGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(3, 4);

        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        graph3.addEdge(1, 2);
        graph3.addEdge(2, 4);

        assertNotEquals(graph1, graph2,
            "Graphs of different types with different edges should not be equal.");
        assertNotEquals(graph2, graph3,
            "Graphs of different types with different edges should not be equal.");
        assertNotEquals(graph1, graph3,
            "Graphs of different types with different edges should not be equal.");
    }

    @Test
    void testEqualsGraphsWithDifferentVertices() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyMatrixGraph();
        Graph graph3 = new IncidenceMatrixGraph();

        graph1.addEdge(1, 2);
        graph2.addEdge(1, 2);
        graph3.addEdge(1, 2);

        graph1.addVertex(3);
        graph3.addVertex(4);// Добавляем дополнительную вершину в graph1

        assertNotEquals(graph1, graph2, "Graphs with different vertices should not be equal.");
        assertNotEquals(graph2, graph3, "Graphs with different vertices should not be equal.");
        assertNotEquals(graph1, graph3, "Graphs with different vertices should not be equal.");
    }

    @Test
    void testEqualsEmptyGraphs() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyListGraph();
        Graph graph3 = new AdjacencyListGraph();

        assertEquals(graph1, graph2, "Empty graphs should be equal.");
        assertEquals(graph2, graph3, "Empty graphs should be equal.");
        assertEquals(graph1, graph3, "Empty graphs should be equal.");
    }

    @Test
    void testEqualsSelfComparison() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(graph, graph, "Graph should be equal to itself.");
    }

    @Test
    void testEqualsNull() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addEdge(1, 2);

        assertNotEquals(graph, null, "Graph should not be equal to null.");
    }

    @Test
    void testEqualsDifferentObject() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addEdge(1, 2);

        String notAGraph = "Not a graph";

        assertNotEquals(graph, notAGraph,
            "Graph should not be equal to an object of a different type.");
    }

    @Test
    void testHashCodeConsistency() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyMatrixGraph();

        graph1.addEdge(1, 2);
        graph2.addEdge(1, 2);

        assertEquals(graph1.hashCode(), graph2.hashCode(),
            "Equal graphs should have the same hash code.");
    }

    @Test
    void testHashCodeDifference() {
        Graph graph1 = new AdjacencyMatrixGraph();
        Graph graph2 = new AdjacencyMatrixGraph();

        graph1.addEdge(1, 2);
        graph2.addEdge(2, 3);

        assertNotEquals(graph1.hashCode(), graph2.hashCode(),
            "Graphs with different edges should have different hash codes.");
    }
}
