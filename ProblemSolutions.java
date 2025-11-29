/******************************************************************
 *
 *   Julia Harper / Data Structure II -#8
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     *
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     *
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     *
     * You goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     *
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     *
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     *
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     *
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     *
     *     Example 1:
     *     Input: numExams = 2, prerequisites = [[1,0]]
     *     Output: true
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0. So, it is possible (no
     *     cyclic or cycle graph of prereqs).
     *
     *
     *     Example 2:
     *     Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     *     Output: false
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0, and to take exams 0 you
     *     should also have completed the certification of exam
     *     1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams          - number of exams, which will produce a graph of n nodes
     * @param prerequisites     - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */
    public boolean canFinish(int numExams, int[][] prerequisites) {
        int numNodes = numExams;  // # of nodes in graph

        // Build directed graph's adjacency list
        ArrayList<Integer>[] adj = getAdjList(numExams, prerequisites);

        // visited array to track nodes already fully processed
        boolean[] visited = new boolean[numNodes];
        // recStack array to track nodes in the current recursion stack
        boolean[] recStack = new boolean[numNodes];

        // check all nodes for cycles using DFS
        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                if (isCyclicDFS(i, adj, visited, recStack)) {
                    return false; // cycle detected
                }
            }
        }

        return true; // no cycle detected, exams can be completed
    }

    /**
     * Helper method to build adjacency list from edge list
     */
    private ArrayList<Integer>[] getAdjList(int numNodes, int[][] edges) {
        ArrayList<Integer>[] adj = new ArrayList[numNodes];
        for (int node = 0; node < numNodes; node++) {
            adj[node] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]); // Add directed edge from [0] -> [1]
        }
        return adj;
    }

    /**
     * DFS to detect cycle in a directed graph
     */
    private boolean isCyclicDFS(int node, ArrayList<Integer>[] adj, boolean[] visited, boolean[] recStack) {
        if (recStack[node]) return true; // cycle found
        if (visited[node]) return false;

        visited[node] = true;
        recStack[node] = true;

        for (int neighbor : adj[node]) {
            if (isCyclicDFS(neighbor, adj, visited, recStack)) {
                return true;
            }
        }

        recStack[node] = false;
        return false;
    }

    // ----------------- numGroups() -----------------

    /**
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
     *     AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 are connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form one large group.
     */
    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length;
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Converting adjacency matrix to adjacency list
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjMatrix[i][j] == 1 && i != j) {
                    graph.putIfAbsent(i, new ArrayList<>());
                    graph.putIfAbsent(j, new ArrayList<>());
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        boolean[] visited = new boolean[numNodes];
        int groupCount = 0;

        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                dfsGroup(i, graph, visited);
                groupCount++; // new connected component found
            }
        }

        return groupCount;
    }

    /**
     * DFS helper for numGroups
     */
    private void dfsGroup(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
        visited[node] = true;
        if (!graph.containsKey(node)) return;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) dfsGroup(neighbor, graph, visited);
        }
    }

} // end class
