import java.util.*;

public class IntuitiveTopological implements TopologicalSort {
    private BetterDiGraph graph;
    private List<Integer> order;
    private boolean isDAG;

    public IntuitiveTopological(BetterDiGraph graph) {
        this.graph = graph;
        this.order = new ArrayList<>();
        this.isDAG = true;
        performTopologicalSort();
    }

    @Override
    public Iterable<Integer> order() {
        return order;
    }

    @Override
    public boolean isDAG() {
        return isDAG;
    }

    private void performTopologicalSort() {
        // Create a copy of the original graph to avoid modifying it
        BetterDiGraph copyGraph = new BetterDiGraph();
        for (Integer vertex : graph.vertices()) {
            copyGraph.addVertex(vertex);
            for (Integer adjVertex : graph.getAdj(vertex)) {
                copyGraph.addEdge(vertex, adjVertex);
            }
        }

        // Perform the topological sort
        while (!copyGraph.isEmpty()) {
            boolean foundIndegreeZeroVertex = false;
            for (Integer vertex : copyGraph.vertices()) {
                if (copyGraph.getIndegree(vertex) == 0) {
                    order.add(vertex);
                    removeVertexAndUpdateIndegree(copyGraph, vertex);
                    foundIndegreeZeroVertex = true;
                    break;
                }
            }
            if (!foundIndegreeZeroVertex) {
                isDAG = false; // Graph has a cycle
                break;
            }
        }
    }

    private void removeVertexAndUpdateIndegree(BetterDiGraph graph, int vertex) {
        for (Integer adjVertex : graph.getAdj(vertex)) {
            graph.removeEdge(vertex, adjVertex);
        }
        graph.removeVertex(vertex);
    }
}
