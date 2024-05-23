import java.util.*;

class Vertex {
    long id;

    public Vertex(long id) {
        this.id = id;
    }
}

class Edge {
    Vertex from;
    Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }
}

class Graph {
    private Map<Vertex, List<Edge>> adjacencyList;
    private Set<Vertex> vertices;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
    }

    // IF THE GRAPH CONTAINS UNCONNECTED VERTEX, YOU CAN USE THIS METHOD; LET'S SAY OUR GRAPH IS COMPOSED OF ONLY ONE VERTEX
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        adjacencyList.computeIfAbsent(edge.from, k -> new ArrayList<>()).add(edge);
        vertices.add(edge.from);
        vertices.add(edge.to);
    }

    /**
     * SOLUTION 1 USING DEPTH FIRST SEARCH WITH RECURSION
     * TIME COMPLEXITY: O(V + E)
     * SPACE COMPLEXITY: O(V + E)
     *
     * THE SOLUTION WILL ACCESS ONCE EVERY SINGLE VERTEX CALCULATING IN THE SAME TIME THE LONGEST PATH
     *
     * @param vertex
     * @param distance
     * @param visited
     * @return
     */
    private long dfs(Vertex vertex, Map<Vertex, Long> distance, Set<Vertex> visited) {
        if (visited.contains(vertex)) {
            return distance.get(vertex);
        }

        visited.add(vertex);
        long maxDistance = 0;

        if (adjacencyList.get(vertex) != null) {
            for (Edge edge : adjacencyList.get(vertex)) {
                long currentDistance = dfs(edge.to, distance, visited);
                maxDistance = Math.max(maxDistance, currentDistance + 1);
            }
        }

        distance.put(vertex, maxDistance);
        return maxDistance;
    }

    public void longestPath(Vertex startVertex) {
        Map<Vertex, Long> distance = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();

        long longestPathLength = dfs(startVertex, distance, visited);
        System.out.println("Longest Path Length from Vertex " + startVertex.id + " : " + longestPathLength);
    }

    /**
     * SOLUTION 2 USING TOPOLOGICAL SORTING FOLLOWED BY A DYNAMIC PROGRAMMING APPROACH
     * => PREVIOUS SOLUTION MAY CAUSE STACKOVERFLOW FOR LARGE GRAPHS BECAUSE OF RECURSION
     * => THIS SOLUTION AVOIDS PITFALLS OF RECURSION AND LARGE CALL STACKS
     * TIME COMPLEXITY: O(V + E)
     * SPACE COMPLEXITY: O(V + E)
     * @param v
     * @param visited
     * @param stack
     */
    private void topologicalSortUtil(Vertex v, Set<Vertex> visited, Stack<Vertex> stack) {
        visited.add(v);
        if (adjacencyList.get(v) != null) {
            for (Edge edge : adjacencyList.get(v)) {
                if (!visited.contains(edge.to)) {
                    topologicalSortUtil(edge.to, visited, stack);
                }
            }
        }
        stack.push(v);
    }

    private List<Vertex> topologicalSort() {
        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> visited = new HashSet<>();

        for (Vertex vertex : vertices) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        List<Vertex> sortedOrder = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedOrder.add(stack.pop());
        }

        return sortedOrder;
    }

    public void longestPathOptimized(Vertex startVertex) {

        // IF THE GRAPH IS EMPTY
        if (vertices.isEmpty()) {
            System.out.println("The graph is empty.");
            return;
        }

        Map<Vertex, Long> distance = new HashMap<>();
        for (Vertex vertex : vertices) {
            distance.put(vertex, Long.MIN_VALUE);
        }
        distance.put(startVertex, 0L);

        List<Vertex> topologicalOrder = topologicalSort();

        for (Vertex vertex : topologicalOrder) {
            if (distance.get(vertex) != Long.MIN_VALUE) {
                if (adjacencyList.get(vertex) != null) {
                    for (Edge edge : adjacencyList.get(vertex)) {
                        if (distance.get(edge.to) < distance.get(vertex) + 1) {
                            distance.put(edge.to, distance.get(vertex) + 1);
                        }
                    }
                }
            }
        }

        long longestPathLength = 0;
        for (long dist : distance.values()) {
            if (dist > longestPathLength) {
                longestPathLength = dist;
            }
        }

        System.out.println("Longest Path Length from Vertex (Optimized solution) " + startVertex.id + " : " + longestPathLength);
    }
}

public class Main {
    public static void main(String[] args) {
        Vertex v0 = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);
        Vertex v6 = new Vertex(6);

        Graph graph = new Graph();
//        graph.addVertex(v0);
        graph.addEdge(new Edge(v0, v1));
        graph.addEdge(new Edge(v0, v2));
        graph.addEdge(new Edge(v0, v5));
        graph.addEdge(new Edge(v1, v3));
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v2, v3));
        graph.addEdge(new Edge(v2, v4));
        graph.addEdge(new Edge(v3, v4));
        graph.addEdge(new Edge(v5, v4));

        Vertex startVertex = v0;
        graph.longestPath(startVertex);
        graph.longestPathOptimized(startVertex);
    }
}