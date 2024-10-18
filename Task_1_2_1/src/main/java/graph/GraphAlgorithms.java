package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code GraphAlgorithms} class provides static methods for performing common graph algorithms.
 * Currently, it includes a method for performing a topological sort on a directed graph.
 */
public class GraphAlgorithms {

    /**
     * Performs a topological sort on the given directed acyclic graph (DAG). The topological sort
     * orders the vertices such that for every directed edge from vertex A to vertex B, vertex A
     * comes before vertex B in the ordering.
     *
     * @param graph the directed graph to be sorted.
     * @return a list of vertices in topologically sorted order.
     * @throws IllegalArgumentException if the graph contains a cycle and cannot be topologically
     *                                  sorted.
     */
    public static List<Integer> topologicalSort(Graph graph) throws IllegalArgumentException {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();

        for (Integer vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                if (!topologicalSortUtil(graph, vertex, visited, recursionStack, sorted)) {
                    throw new IllegalArgumentException(
                        "Graph has a cycle, topological sort not possible.");
                }
            }
        }

        Collections.reverse(sorted);
        return sorted;
    }

    /**
     * Recursive helper method for performing depth-first search to facilitate topological sorting.
     *
     * @param graph          the graph being sorted.
     * @param vertex         the current vertex being visited.
     * @param visited        the set of vertices that have been visited.
     * @param recursionStack the set of vertices currently in the recursion stack (used to detect
     *                       cycles).
     * @param sorted         the list that accumulates the sorted vertices.
     * @return {@code true} if the graph is acyclic up to this point; {@code false} if a cycle is
     * detected.
     */
    private static boolean topologicalSortUtil(Graph graph, Integer vertex, Set<Integer> visited,
        Set<Integer> recursionStack, List<Integer> sorted) {
        visited.add(vertex);
        recursionStack.add(vertex);

        for (Integer neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                if (!topologicalSortUtil(graph, neighbor, visited, recursionStack, sorted)) {
                    return false;
                }
            } else if (recursionStack.contains(neighbor)) {
                // A cycle is detected
                return false;
            }
        }

        recursionStack.remove(vertex);
        sorted.add(vertex);
        return true;
    }
}
