import java.util.*;

public class BetterDiGraph implements EditableDiGraph {
    private Map<Integer, Set<Integer>> adjList;
    private int edgeCount;

    public BetterDiGraph() {
        adjList = new HashMap<>();
        edgeCount = 0;
    }

    @Override
    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);
        adjList.get(v).add(w);
        edgeCount++;
    }

    @Override
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new HashSet<>());
    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        return adjList.getOrDefault(v, Collections.emptySet());
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {
        if (!adjList.containsKey(v)) {
            throw new NoSuchElementException("Vertex does not exist: " + v);
        }
        int indegree = 0;
        for (Set<Integer> neighbors : adjList.values()) {
            if (neighbors.contains(v)) {
                indegree++;
            }
        }
        return indegree;
    }

    @Override
    public int getVertexCount() {
        return adjList.size();
    }

    @Override
    public void removeEdge(int v, int w) {
        if (adjList.containsKey(v)) {
            Set<Integer> neighbors = adjList.get(v);
            if (neighbors.contains(w)) {
                neighbors.remove(w);
                edgeCount--;
            }
        }
    }

    @Override
    public void removeVertex(int v) {
        if (adjList.containsKey(v)) {
            adjList.remove(v);
            for (Set<Integer> neighbors : adjList.values()) {
                neighbors.remove(v);
            }
            edgeCount -= getIndegree(v);
        }
    }

    @Override
    public Iterable<Integer> vertices() {
        return adjList.keySet();
    }

    @Override
    public boolean isEmpty() {
        return adjList.isEmpty();
    }

    @Override
    public boolean containsVertex(int v) {
        return adjList.containsKey(v);
    }
}
