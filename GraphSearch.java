import java.util.Arrays;

public class GraphSearch {
    private Node allCandidates[];
    private int taken[];
    private int nodesMatrix[][];
    private boolean finished = false, jumpback = false;
    private int minBandwidth;
    private int minmaxBandwidth[];
    private int currMax, currMaxIndex;

    /**
     * Initializes GraphSearch fields
     * 
     * @param allCandidates The array of nodes of the graph
     */
    public GraphSearch(Node allCandidates[], int nodesMatrix[][]) {
        //this.nodes = nodes;
        this.nodesMatrix = nodesMatrix;
        this.allCandidates = allCandidates;
        taken = new int[allCandidates.length];
        minBandwidth = nodesMatrix.length;
        minmaxBandwidth = new int[allCandidates.length];
        currMax = 0;
        currMaxIndex = -1;

        Arrays.sort(allCandidates);

        /*for(int i = 0; i< allCandidates.length; i++){
            System.out.println("Node ID "+ allCandidates[i].getID() + "   DEGREE " + allCandidates[i].getDegree());
        }*/
        
    }

    void backtrack(int solution[], int currIndex) {
        int candidates[] = new int[solution.length - currIndex - 1]; /* max candidates for next position */

        if (currIndex != -1 && is_a_solution(solution, currIndex)) {
            process_solution(solution);
            //printArray(solution);
            //System.out.println("^ solution  Min: " + minBandwidth + " currMaxIndex:" +currMaxIndex);
        } else {
            currIndex = currIndex + 1;
            int nc = construct_candidates(solution, candidates, currIndex);
            if(currIndex == 0){
                nc--;
            }
            //System.out.print("CURR MAX: " + currMax + " CURR MAX INDEX " +currMaxIndex + "||| ");
            //printArray(solution);

            for (int candIndex = 0; candIndex < nc; candIndex++) {
                int prevMax = currMax;
                //WIP
                // If able to jumpback, but the currIndex is at the next candidate
                //     Then cancel the jumpback and reset the currMaxIndex
                if (jumpback && currMaxIndex >= currIndex) {
                    currMaxIndex = currIndex;
                    jumpback = false;
                }
                if(jumpback){
                    return;
                }

                makeMove(solution, candidates, currIndex, candIndex);
                
                //System.out.println("CurrMaxIndex: " + currMaxIndex);

                if(currMax < minBandwidth){
                    //System.out.print("Passed:");
                    //printArray(solution);

                    backtrack(solution, currIndex);
                }
                //printArray(solution);
                currMax = prevMax;
                unmakeMove(solution, candidates, currIndex, candIndex);
                if (finished) {
                    return; /* terminate early */
                }
            }
        }
    }

    

    public boolean is_a_solution(int solution[], int currIndex) {
        if (currIndex == solution.length - 1){
            return true;
        }
        return false;

    }

    private void process_solution(int[] solution) {
        //System.out.println("------------------------");
        //System.out.print("Curr array: ");
        //printArray(a);
        jumpback = true;
        if (currMax < minBandwidth) {
            minBandwidth = currMax;
            minmaxBandwidth = solution.clone();
        }

        if (minBandwidth == 1) {
            finished = true;
        }
        //System.out.println("Min bandwidth: " + minBandwidth);
        
        
    }


    private int construct_candidates(int solution[], int candidates[], int currIndex) {
        int index = 0;
        for (int i = 0; i < allCandidates.length; i++) {
            if(taken[allCandidates[i].getID() - 1] != 1){   // If node is not taken then add it to candidate
                candidates[index] = allCandidates[i].getID();
                index++;
            }
        }
        return index;
    }

    
    private void makeMove(int solution[], int candidates[], int currIndex, int candIndex){
        solution[currIndex] = candidates[candIndex]; // Places the current candidate into the solution at currIndex
        taken[candidates[candIndex] - 1] = 1; // Marks that candidate as taken
        
        int maxLength = findMaxLength(solution, solution[currIndex], currIndex); // Max length from the currNode to components
        if(maxLength > currMax){

            currMax = maxLength;
            currMaxIndex = currIndex;
        }
    }

    /**
     * This finds the max length from the current node to its connected nodes that
     * are in partial solution[]
     * 
     * @param solution  Partial solution
     * @param currNode  The current node recently inserted
     * @param currIndex The index of the current node in partial solution[]
     * @return The max length between current node and its connections
     */
    public int findMaxLength(int solution[], int currNode, int currIndex) {
        int maxLength = 0;

        // Loops through each node that's connected to currNode
        for (int i = 0; i < currIndex; i++) {
            if(nodesMatrix[currNode -1][solution[i] - 1] == 1){
                if(currIndex - i > maxLength)
                    maxLength = currIndex - i;
            }
        }

        return maxLength;
    }

    private void unmakeMove(int solution[], int candidates[], int currIndex, int candIndex) {
        solution[currIndex] = -1; // Undos the node at the currIndex
        taken[candidates[candIndex] - 1] = 0; // Remove candidate from taken
    }

    public int[] getMinMaxBandwidth(){
        System.out.println("Bandwidth: " + minBandwidth);
        return minmaxBandwidth;
    }

    public boolean possibleWithMin2(){
        return true;
    }
    
    public void printArray(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
