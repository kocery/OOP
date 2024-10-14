package graph;

import java.util.Objects;

/**
 * The {@code Edge} class represents an edge in a graph. It consists of a source vertex (from) and a
 * destination vertex (to). This class is immutable and is used to represent edges in various graph
 * implementations.
 */
public class Edge {

    public final int from;
    public final int to;

    /**
     * Constructs an edge from the specified source vertex to the specified destination vertex.
     *
     * @param from the source vertex where the edge starts.
     * @param to   the destination vertex where the edge ends.
     */
    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Compares this edge to the specified object for equality. Returns {@code true} if and only if
     * the specified object is also an edge and both edges have the same source and destination
     * vertices.
     *
     * @param obj the object to compare this edge against.
     * @return {@code true} if the specified object is equal to this edge; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Edge other)) {
            return false;
        }
        return from == other.from && to == other.to;
    }

    /**
     * Returns the hash code value for this edge. The hash code is computed based on the source and
     * destination vertices.
     *
     * @return the hash code value for this edge.
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
