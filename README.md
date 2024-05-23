Solution 1:
- We perform a DFS starting from each vertex. During the DFS traversal, we recursively visit each vertex's adjacent vertices.
- We use a set to keep track of visited vertices to avoid reprocessing them. This ensures each vertex is processed only once.
- For each vertex, we calculate the distance from the start vertex to all reachable vertices. This distance is updated if a longer path is found during traversal.
- Each vertex maintains its longest distance from the start vertex. The distance is updated based on the distances of its adjacent vertices plus one (indicating a move from the current vertex to the adjacent vertex).

Solution 2:
- We start by performing a topological sort on the DAG. Topological sorting of a graph is a linear ordering of its vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
- We use Depth First Search (DFS) to achieve this. We recursively visit each vertex and push it onto a stack once all its adjacent vertices have been visited. The stack will contain the vertices in topologically sorted order once the DFS is complete.
- After obtaining the topological sort, we initialize a distance map where the distance to each vertex is set to negative infinity (Long.MIN_VALUE) to signify that those vertices are initially unreachable.
- We set the distance to the starting vertex (or the first vertex in topological order) to 0, as the longest path to itself is zero.
- We then process each vertex in topological order. For each vertex, we update the distances to its adjacent vertices. If the distance to an adjacent vertex can be increased by taking the current vertex's path plus one, we update the distance to that vertex.

# Does the solution work for larger graphs?
Answer: Yes
# Can you think of any optimizations?
Answer: Solution 1 is based on DFS using recursion, but it may lead to stackoverflow if the graph is too large. 
That's why Solution 2 is using topological sorting followed by dynamic programming approach to handle large graphs.
# What’s the computational complexity of your solution?
Both of the solutions are:
Time complexity: O(V + E) 
Space complexity: O(V + E)
# Are there any unusual cases that aren&#39;t handled?
- The graph is empty: HANDLED
- The graph has vertices but no edges connecting them: HANDLED
- The graph contains only one vertex with no edges: HANDLED