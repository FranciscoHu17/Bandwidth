import java.util.*;

public class Node implements Comparable<Node>{
    private int nodeID;
    private int degree;

    /**
     * Initializes the Node fields
     * 
     * @param id The ID of the particular Node
     */
    public Node(int id) {
        nodeID = id;
        degree = 0;
    }

    /**
     * Gets the Node ID
     * 
     * @return The Node ID
     */
    public int getID() {
        return nodeID;
    }

    public void incrementDegree(){
        degree++;
    }

    public int getDegree(){
        return degree;
    }

    @Override
    public int compareTo(Node o) {
        // TODO Auto-generated method stub
        return  degree - o.getDegree();
    }
}