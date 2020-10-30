import java.util.*;

public class Node {
    private int nodeID;
    private ArrayList<Node> connected;

    /**
     * Initializes the Node fields
     * 
     * @param id The ID of the particular Node
     */
    public Node(int id) {
        nodeID = id;
        connected = new ArrayList<>();
    }

    /**
     * Gets the Node ID
     * 
     * @return The Node ID
     */
    public int getID() {
        return nodeID;
    }

    /**
     * Gets the array of connected nodes
     * 
     * @return Array of connected nodes
     */
    public Node[] getConnected() {
        Node connectedArr[] = new Node[connected.size()];
        return connected.toArray(connectedArr);
    }

    public boolean isConnected(Node other) {
        for (int i = 0; i < connected.size(); i++) {
            if (other == connected.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a node to the list of connected nodes
     * 
     * @param node Node to add to the list
     */
    public void addConnectedNode(Node node) {
        connected.add(node);
    }
}