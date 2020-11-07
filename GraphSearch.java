import java.util.Arrays;

public class GraphSearch {
    private Node nodes[];
    private boolean finished = false, jumpback = false;
    private int minBandwidth;
    private Node minmaxBandwidth[];
    private int currMax, currMaxIndex;

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
        currMaxIndex = -1;
        Arrays.sort(nodes);

        /*for(int i = 0; i< nodes.length; i++){
            System.out.println("Node ID "+ nodes[i].getID() + "   DEGREE " + nodes[i].getDegree());
        }*/
        
    }

    void backtrack(Node solution[], int used[], int currIndex, int n) {
        Node candidates[] = new Node[solution.length - currIndex - 1]; /* max candidates for next position */

        if (currIndex != -1 && is_a_solution(solution, used, currIndex, n)) {
            process_solution(solution, used, n);
            //System.out.println("^ solution  Min: " + minBandwidth + " currMaxIndex:" +currMaxIndex);
        } else {
            currIndex = currIndex + 1;
            int nc = construct_candidates(used, currIndex, candidates, n);
            //printArray(solution);

            for (int candIndex = 0; candIndex < nc; candIndex++) {
                int prevMax = currMax;
                /*if (currMaxIndex != -1 && currMaxIndex < currIndex && jumpback) {
                    return;
                }
                else if(currMaxIndex != -1 && currMaxIndex >= currIndex && jumpback){
                    currMaxIndex = -1;
                    jumpback = false;
                }*/
                
                //WIP
                // If able to jumpback, but the currIndex is at the next candidate
                //     Then cancel the jumpback and reset the currMaxIndex
                if (jumpback && currMaxIndex >= currIndex) {
                    currMaxIndex = -1;
                    jumpback = false;
                }
                if(jumpback){
                    return;
                }

                makeMove(solution, candidates, used, currIndex, candIndex);
                //printArray(solution);
                //System.out.println("CurrMaxIndex: " + currMaxIndex);

                if(currMax < minBandwidth){
                    //System.out.print("Passed:");
                    //printArray(solution);

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

    

    public boolean is_a_solution(Node solution[], int used[], int currIndex, int n) {
        if (currIndex == solution.length - 1){
            return true;
        }
        return false;

    }

    private void process_solution(Node[] a, int used[], int n) {
        //System.out.println("------------------------");
        //System.out.print("Curr array: ");
        //printArray(a);
        jumpback = true;
        if (currMax < minBandwidth) {
            minBandwidth = currMax;
            minmaxBandwidth = a.clone();
        }

        if (minBandwidth == 1) {
            finished = true;
        }
        //System.out.println("Min bandwidth: " + minBandwidth);
        
        
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
            currMaxIndex = currIndex;
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

        // Loops through each node that's connected to currNode
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

    private void unmakeMove(Node solution[], Node candidates[], int used[], int currIndex, int candIndex) {
        solution[currIndex] = null; // Undos the node at the currIndex
        used[candidates[candIndex].getID() - 1] = -1; // The node is no longer in solution[]
    }

    public Node[] getMinMaxBandwidth(){
        System.out.println("Bandwidth: " + minBandwidth);
        return minmaxBandwidth;
    }

    public boolean possibleWithMin2(){
        return true;
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
