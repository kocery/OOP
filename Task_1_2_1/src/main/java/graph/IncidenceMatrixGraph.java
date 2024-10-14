package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * {@code IncidenceMatrixGraph} represents a directed graph using an incidence matrix.
 * The graph allows adding and removing vertices and edges, as well as retrieving neighbors for a given vertex.
 */
public class IncidenceMatrixGraph implements Graph {

    private int[][] incidenceMatrix;
    private Map<Integer, Integer> vertexToIndex;
    private Map<Integer, Integer> indexToVertex;
    private List<Edge> edges;

    /**
     * Constructs an empty graph using an incidence matrix.
     */
    public IncidenceMatrixGraph() {
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new HashMap<>();
        this.edges = new ArrayList<>();
        this.incidenceMatrix = new int[0][0];
    }

    @Override
    public void addVertex(int vertex) {
        if (vertexToIndex.containsKey(vertex)) {
            return;
        }
        int index = vertexToIndex.size();
        vertexToIndex.put(vertex, index);
        indexToVertex.put(index, vertex);

        // Расширяем матрицу инцидентности
        int vertexCount = vertexToIndex.size();
        int edgeCount = edges.size();

        int[][] newMatrix = new int[vertexCount][edgeCount];
        for (int i = 0; i < vertexCount - 1; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
        }
        incidenceMatrix = newMatrix;
    }

    @Override
    public void removeVertex(int vertex) {
        Integer idx = vertexToIndex.get(vertex);
        if (idx == null) {
            return;
        }
        vertexToIndex.remove(vertex);
        indexToVertex.remove(idx);

        // Обновляем индексы оставшихся вершин
        Map<Integer, Integer> newVertexToIndex = new HashMap<>();
        Map<Integer, Integer> newIndexToVertex = new HashMap<>();
        int newIndex = 0;
        for (Map.Entry<Integer, Integer> entry : vertexToIndex.entrySet()) {
            int v = entry.getKey();
            if (entry.getValue() > idx) {
                newVertexToIndex.put(v, newIndex);
                newIndexToVertex.put(newIndex, v);
                newIndex++;
            } else {
                newVertexToIndex.put(v, entry.getValue());
                newIndexToVertex.put(entry.getValue(), v);
            }
        }
        vertexToIndex = newVertexToIndex;
        indexToVertex = newIndexToVertex;

        // Удаляем связанные ребра
        edges.removeIf(edge -> edge.from == vertex || edge.to == vertex);

        rebuildIncidenceMatrix();
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        if (!vertexToIndex.containsKey(fromVertex)) {
            addVertex(fromVertex);
        }
        if (!vertexToIndex.containsKey(toVertex)) {
            addVertex(toVertex);
        }
        Edge edge = new Edge(fromVertex, toVertex);
        if (edges.contains(edge)) {
            return;
        }
        edges.add(edge);
        rebuildIncidenceMatrix();
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        Edge edge = new Edge(fromVertex, toVertex);
        edges.remove(edge);
        rebuildIncidenceMatrix();
    }

    /**
     * Rebuilds the incidence matrix after changes in the list of vertices or edges. This method
     * updates the incidence matrix to reflect the current state of the graph.
     */
    private void rebuildIncidenceMatrix() {
        int vertexCount = vertexToIndex.size();
        int edgeCount = edges.size();
        incidenceMatrix = new int[vertexCount][edgeCount];
        for (int e = 0; e < edgeCount; e++) {
            Edge edge = edges.get(e);
            int fromIdx = vertexToIndex.get(edge.from);
            int toIdx = vertexToIndex.get(edge.to);
            incidenceMatrix[fromIdx][e] = 1;
            incidenceMatrix[toIdx][e] = 2;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();

        Integer vertexIdx = vertexToIndex.get(vertex);
        if (vertexIdx == null) {
            return neighbors;
        }

        int edgeCount = edges.size();
        for (int edgeIdx = 0; edgeIdx < edgeCount; edgeIdx++) {
            if (incidenceMatrix[vertexIdx][edgeIdx] == 1) {
                for (int v = 0; v < vertexToIndex.size(); v++) {
                    if (incidenceMatrix[v][edgeIdx] == 2) {
                        neighbors.add(indexToVertex.get(v));
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexToIndex.keySet());
    }

    @Override
    public void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        int vertexCount = lines.size();
        int edgeCount = lines.getFirst().trim().split("\\s+").length;

        incidenceMatrix = new int[vertexCount][edgeCount];
        vertexToIndex = new HashMap<>();
        indexToVertex = new HashMap<>();

        for (int i = 0; i < vertexCount; i++) {
            vertexToIndex.put(i, i);
            indexToVertex.put(i, i);
            String[] line = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < edgeCount; j++) {
                incidenceMatrix[i][j] = Integer.parseInt(line[j]);
            }
        }

        edges = new ArrayList<>();
        for (int j = 0; j < edgeCount; j++) {
            int from = -1;
            int to = -1;
            for (int i = 0; i < vertexCount; i++) {
                if (incidenceMatrix[i][j] == 1) {
                    from = indexToVertex.get(i);
                } else if (incidenceMatrix[i][j] == 2) {
                    to = indexToVertex.get(i);
                }
            }
            if (from != -1 && to != -1) {
                edges.add(new Edge(from, to));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int vertexCount = vertexToIndex.size();
        int edgeCount = edges.size();
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edgeSet = new HashSet<>();
        for (Edge edge : edges) {
            edgeSet.add(new Edge(edge.from, edge.to));
        }
        return edgeSet;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }

        Set<Integer> thisVertices = new HashSet<>(this.getVertices());
        Set<Integer> otherVertices = new HashSet<>(other.getVertices());
        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        Set<Edge> thisEdges = this.getEdges();
        Set<Edge> otherEdges = other.getEdges();
        return thisEdges.equals(otherEdges);
    }

    @Override
    public int hashCode() {
        Set<Integer> vertices = new HashSet<>(this.getVertices());
        Set<Edge> edges = this.getEdges();
        return Objects.hash(vertices, edges);
    }
}
