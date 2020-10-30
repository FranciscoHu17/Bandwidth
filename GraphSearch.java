import java.util.*;

public class GraphSearch {
    private Node nodes[];
    private Node parent[];
    private boolean processed[], discovered[];
    private boolean finished = false;
    private int min;
    private Node minmaxBandwidth[];

    /**
     * Initializes GraphSearch fields
     * 
     * @param nodes The array of nodes of the graph
     */
    public GraphSearch(Node nodes[]) {
        this.nodes = nodes;
        parent = new Node[nodes.length];
        processed = new boolean[nodes.length];
        discovered = new boolean[nodes.length];
        min = nodes.length;
    }

    /**
     * BFS on the graph
     * 
     * @param start The starting node to search from
     */
    public void bfs(Node start) {
        Queue<Node> queue = new LinkedList<>();

        queue.add(start);
        discovered[start.getID() - 1] = true;

        while (queue.size() != 0) {
            Node current = queue.poll();
            processed[current.getID() - 1] = true;
            System.out.print(current.getID() + " ");

            Node connected[] = current.getConnected();
            for (int i = 0; i < connected.length; i++) {
                Node successor = connected[i];

                if (!discovered[successor.getID() - 1]) {
                    queue.add(successor);
                    discovered[successor.getID() - 1] = true;
                    parent[successor.getID() - 1] = current;
                }
            }
        }
    }

    /**
     * Prints out all of the components of the graph and the nodes in the components
     */
    public void connected_component() {
        int numOfComponents = 1;
        for (int i = 0; i < nodes.length; i++) {
            if (!processed[i]) {
                System.out.println("Component " + numOfComponents + ": ");
                bfs(nodes[i]);
                System.out.println("\n");
                numOfComponents++;
            }
        }
    }

    void backtrack(Node solution[], int used[], int currIndex, int n) {
        Node candidates[] = new Node[solution.length - currIndex - 1]; /* max candidates for next position */

        if (is_a_solution(solution, currIndex, n)) {
            process_solution(solution, currIndex, n);
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
                    return; /* terminate early */
                }
            }
        }
    }

    public int findMaxLength(int used[], Node currNode) {
        Node connected[] = currNode.getConnected();
        for (int i = 0; i < connected.length; i++) {

        }

        return -1;
    }

    public int maxBandwidth(Node a[]) {
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            int dist = 0;
            for (int j = 0; j < a.length; j++) {
                if (i != j && dist < Math.abs(j - i) && a[i].isConnected(a[j])) {
                    dist = Math.abs(j - i);
                }
            }
            if (dist > max)
                max = dist;
        }
        return max;
    }

    public boolean is_a_solution(Node a[], int currIndex, int n) {
        if (currIndex == a.length - 1)
            return true;
        return false;
    }

    private void process_solution(Node[] a, int currIndex, int n) {
        int bandwidth = maxBandwidth(a);
        if (bandwidth < min) {
            min = bandwidth;
            minmaxBandwidth = a;
        }

        System.out.println("Max bandwidth: " + bandwidth);
        System.out.println("Min bandwidth: " + min);
        printArray(a);
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
