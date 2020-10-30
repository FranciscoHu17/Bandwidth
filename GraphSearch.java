public class GraphSearch {
    private Node nodes[];
    private boolean finished = false;
    private int minBandwidth;
    private Node minmaxBandwidth[];
    private int currMax;

    /**
     * Initializes GraphSearch fields
     * 
     * @param nodes The array of nodes of the graph
     */
    public GraphSearch(Node nodes[]) {
        this.nodes = nodes;
        minBandwidth = nodes.length;
        minmaxBandwidth = new Node[nodes.length];
        currMax = 0;
    }

    void backtrack(Node solution[], int used[], int currIndex, int n) {
        Node candidates[] = new Node[solution.length - currIndex - 1]; /* max candidates for next position */

        if (currIndex != -1 && is_a_solution(solution, used, currIndex, n)) {
            process_solution(solution, used, n);
        } else {
            currIndex = currIndex + 1;
            int nc = construct_candidates(used, currIndex, candidates, n);
            // printArray(solution);

            for (int candIndex = 0; candIndex < nc; candIndex++) {
                int prevMax = currMax;
                makeMove(solution, candidates, used, currIndex, candIndex);
                //printArray(solution);
                //System.out.println("PrevMax: " +prevMax + "  CurrMax: " + currMax);
                if(currMax < minBandwidth){
                    backtrack(solution, used, currIndex, n);
                }
                currMax = prevMax;
                unmakeMove(solution, candidates, used, currIndex, candIndex);
                if (finished) {
                    return; /* terminate early */
                }
            }
        }
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
            //     Find the length from curr node to connected node
            if (solutionsIndex != -1 && currIndex - solutionsIndex > maxLength) {
                maxLength = currIndex - solutionsIndex;
            }
        }

        return maxLength;
    }

    public boolean is_a_solution(Node solution[], int used[], int currIndex, int n) {
        if (currIndex == solution.length - 1){
            return true;
            //return maxBandwidth(solution, used) < minBandwidth;
        }
        return false;

    }

    private void process_solution(Node[] a, int used[], int n) {
        //System.out.println("------------------------");
        //System.out.print("Curr array: ");
        //printArray(a);
        if (currMax < minBandwidth) {
            minBandwidth = currMax;
            minmaxBandwidth = a.clone();
        }

        if (minBandwidth == 1) {
            finished = true;
        }
        //System.out.println("Min bandwidth: " + minBandwidth);
        
        
    }

    /**
     * Finds the max bandwidth in the fully processed solution
     * (DEPRACATED)
     * 
     * @param solution 
     * @param used
     * @return
     */
    public int maxBandwidth(Node solution[], int used[]) {
        int max = 0;

        // For each node in the solution:
        //     find the max length
        for (int i = 0; i < solution.length; i++) {
            int length = findMaxLength(used, solution[i], i);
            if (max < length) {
                max = length;
            }
        }

        return max;
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

    
    private void makeMove(Node solution[], Node candidates[], int used[], int currIndex, int candIndex){
        solution[currIndex] = candidates[candIndex]; // Places the current candidate into the solution at currIndex
        used[candidates[candIndex].getID() - 1] = currIndex; // Stores the chosen node's location
        
        int maxLength = findMaxLength(used, solution[currIndex], currIndex); // Max length from the currNode to components
        if(maxLength > currMax){
            currMax = maxLength;
        }
    }

    private void unmakeMove(Node solution[], Node candidates[], int used[], int currIndex, int candIndex) {
        solution[currIndex] = null; // Undos the node at the currIndex
        used[candidates[candIndex].getID() - 1] = -1; // The node is no longer in solution[]
    }

    public Node[] getMinMaxBandwidth(){
        return minmaxBandwidth;
    }

    public void printArray(Node arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null)
                System.out.print(arr[i].getID() + " ");
            else
                System.out.print("null ");
        }
        System.out.println();
    }
}
