/******************************************************************
 *
 *   Julia Harper /Data Structure II -#8
 *
 *   Note, additional comments provided throughout this source code
 *   is for educational purposes
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  Graph traversal exercise
 *
 *  The Graph class is a representing an oversimplified Directed Graph of vertices
 *  (nodes) and edges. The graph is stored in an adjacency list
 */
public class Graph {
    int numVertices;                  // vertices in graph
    LinkedList<Integer>[] adjListArr; // Adjacency list
    List<Integer> vertexValues;       // vertex values

    // Constructor 
    public Graph(int numV) {
        numVertices = numV;
        adjListArr = new LinkedList[numVertices];
        vertexValues = new ArrayList<>(numVertices);

        for (int i = 0; i < numVertices; i++) {
            adjListArr[i] = new LinkedList<>();
            vertexValues.add(0);
        }
    }

    /*
     * method setValue
     * 
     * Sets a vertex's (node's) value.
     */ 
    public void setValue(int vertexIndex, int value) {
        if (vertexIndex >= 0 && vertexIndex < numVertices) {
            vertexValues.set(vertexIndex, value);
        } else {
            throw new IllegalArgumentException(
                   "Invalid vertex index: " + vertexIndex);
        }
    }

    /*
     * method addEdge
     * 
     * Adds a directed edge from source vertex to destination vertex.
     */
    public void addEdge(int src, int dest) {
        adjListArr[src].add(dest);
    }

    /*
     * method printGraph
     * 
     * Prints the graph as an adjacency matrix
     */ 
    public void printGraph() {
        System.out.println("\nAdjacency Matrix Representation:\n");
        int[][] matrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (Integer dest : adjListArr[i]) {
                matrix[i][dest] = 1;
            }
        }

        System.out.print("  ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < numVertices; j++) {
                if (matrix[i][j] == 1) {
                    System.out.print("| ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println(); // print newline after each row
        }
    }

    /**
     * method findRoot
     *
     * This method returns the value of the root vertex, where root is defined as
     * a node that has no incoming edges. If no root vertex is found
     * and/or more than one root vertex, then return -1.
     */
    public int findRoot() {
        int rootCandidate = -1; // store candidate root vertex index
        int rootCount = 0;      // count vertices with no incoming edges

        // iterate through all vertices to check if they have incoming edges
        for (int i = 0; i < numVertices; i++) {
            boolean hasIncoming = false;
            for (int j = 0; j < numVertices; j++) {
                if (adjListArr[j].contains(i)) { // vertex i has incoming edge from j
                    hasIncoming = true;
                    break;
                }
            }
            if (!hasIncoming) { 
                rootCandidate = i; // found a vertex with no incoming edges
                rootCount++;       // increment root candidate count
            }
        }

        // if there is not exactly one root candidate, return -1
        if (rootCount != 1) {
            return -1;
        }

        // return the value associated with the unique root vertex
        return vertexValues.get(rootCandidate);
    }
}
