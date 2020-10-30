import java.util.*;

public class GraphSearch {
    private Node nodes[];
    private boolean finished = false;
    private int minBandwidth;
    private Node minmaxBandwidth[];

    /**
     * Initializes GraphSearch fields
     * 
     * @param nodes The array of nodes of the graph
     */
    public GraphSearch(Node nodes[]) {
        this.nodes = nodes;
        minBandwidth = nodes.length;
    }

    void backtrack(Node solution[], int used[], int currIndex, int n) {
        Node candidates[] = new Node[solution.length - currIndex - 1]; /* max candidates for next position */

        // If able to endAttempt (because maxLength >= minBandwidth):
        // then test next candidate
        if (currIndex != -1 && endAttempt(solution, used, currIndex)) {
            return;
        }

        if (currIndex != -1 && is_a_solution(solution, used, currIndex, n)) {
            process_solution(solution, used, n);
        } else {
            currIndex = currIndex + 1;
            int nc = construct_candidates(used, currIndex, candidates, n);
            // printArray(solution);

            for (int i = 0; i < nc; i++) {
                solution[currIndex] = candidates[i];
                used[candidates[i].getID() - 1] = currIndex; // Make move
                backtrack(solution, used, currIndex, n);
                used[candidates[i].getID() - 1] = -1; // Unmake move
                if (finished) {
                    finished = false;
                    return; /* terminate early */
                }
            }
        }
    }

    /**
     * This finds the max length from the current node to its connected nodes that
     * are in partial solution[]
     * 
     * @param used      Array sorted in nodeID order that contains indicies of used
     *                  nodes in partial solution[]
     * @param currNode  The current node recently inserted
     * @param currIndex The index of the current node in partial solution[]
     * @return The max length between current node and its connections
     */
    public int findMaxLength(int used[], Node currNode, int currIndex) {
        Node connected[] = currNode.getConnected();
        int maxLength = 0;

        for (int i = 0; i < connected.length; i++) {
            int nodesIndex = connected[i].getID() - 1; // index of connected node in nodes[]
            int solutionsIndex = used[nodesIndex]; // index of connected node in partial solution[]

            // If the connected node is in partial solutions[] and it is bigger
            // than current length:
            // Find the length from curr node to connected node
            if (solutionsIndex != -1 && currIndex - solutionsIndex > maxLength) {
                maxLength = currIndex - solutionsIndex;
            }
        }

        return maxLength;
    }

    public int maxBandwidth(Node solution[], int used[]) {
        int max = 0;
        // DEPRECATED
        /*
         * for (int i = 0; i < a.length; i++) { int dist = 0; for (int j = 0; j <
         * a.length; j++) { if (i != j && dist < Math.abs(j - i) &&
         * a[i].isConnected(a[j])) { dist = Math.abs(j - i); } } if (dist > max) max =
         * dist; }
         */

        // For each node in the solution:
        // Find the max length of each
        for (int i = 0; i < solution.length; i++) {
            int length = findMaxLength(used, solution[i], i);
            if (max < length) {
                max = length;
            }
        }

        return max;
    }

    public boolean is_a_solution(Node solution[], int used[], int currIndex, int n) {
        if (currIndex == solution.length - 1)
            return true;
        return false;

    }

    /**
     * Abandon the partial solution if max length exceeds or equal to the minimum
     * bandwidth previously found
     * 
     * @param solution
     * @param used
     * @param currIndex
     * @return
     */
    public boolean endAttempt(Node solution[], int used[], int currIndex) {
        int maxLength = findMaxLength(used, solution[currIndex], currIndex);
        if (maxLength >= minBandwidth) {
            return true;
        }
        return false;
    }

    private void process_solution(Node[] a, int used[], int n) {
        int bandwidth = maxBandwidth(a, used);
        if (bandwidth < minBandwidth) {
            minBandwidth = bandwidth;
            minmaxBandwidth = a;
        }

        if (minBandwidth == 1) {
            finished = true;
        }

        System.out.println("Max bandwidth: " + bandwidth);
        System.out.println("Min bandwidth: " + minBandwidth);
        System.out.print("Curr array: ");
        printArray(a);
        System.out.print("Min array: ");
        printArray(minmaxBandwidth);
    }

    private int construct_candidates(int[] used, int k, Node[] candidates, int n) {
        int index = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (used[nodes[i].getID() - 1] == -1) {
                candidates[index] = nodes[i];
                index++;
            }
        }
        return index;
    }

    private void printArray(Node arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null)
                System.out.print(arr[i].getID() + " ");
            else
                System.out.print("null ");
        }
        System.out.println();
    }
}
