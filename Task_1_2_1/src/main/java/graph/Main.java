package graph;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(6, 6);

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

        System.out.println("Граф после добавления вершин и ребра:");
        System.out.println(graph);

        System.out.println(graph.getNeighbors(5));
    }
}
