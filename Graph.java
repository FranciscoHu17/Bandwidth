import java.io.*;

public class Graph {
    private static Node nodes[];

    /**
     * Runs the program that creates the graph by reading the file and outputting
     * the results
     * 
     * @param args
     */
    public static void main(String args[]) {
        try {
            System.out.println("File 1:");
            File file1 = new File("Test Files/g-t-7-6");
            createGraphFromFile(file1);
            GraphSearch file1Search = new GraphSearch(nodes);
            int positions[] = new int[nodes.length];

            for (int i = 0; i < positions.length; i++) {
                positions[i] = -1;
            }

            file1Search.backtrack(new Node[nodes.length], positions, -1, nodes.length - 1);

        } catch (Exception error) {
            System.out.println(error);
        }

    }

    /**
     * Reads a file line by line and creates the graph
     * 
     * @param file File to read
     * @throws Exception
     */
    public static void createGraphFromFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));

        String strline = br.readLine();
        int vertices = Integer.parseInt(strline);
        strline = br.readLine();
        int edges = Integer.parseInt(strline);
        initNodes(vertices);

        System.out.println("Number of Vertices: " + vertices);
        System.out.println("Number of Edges: " + edges + "\n");

        while ((strline = br.readLine()) != null && !strline.equals("")) {
            System.out.println(strline);
            addEdge(strline);
        }

        br.close();
    }

    /**
     * Initializes the nodes[] variable
     * 
     * @param vertices The amount of vertices in the graph
     */
    public static void initNodes(int vertices) {
        nodes = new Node[vertices];
        for (int i = 0; i < vertices; i++) {
            nodes[i] = new Node(i + 1);
        }
    }

    /**
     * Adds an edge based on the string with 2 vertices
     * 
     * @param str Formatted to be "[vertex 1] [vertex2]"
     */
    public static void addEdge(String str) {
        int space = str.indexOf(" ");
        int vertex1 = Integer.parseInt(str.substring(0, space));
        int vertex2 = Integer.parseInt(str.substring(space + 1).trim());
        nodes[vertex1 - 1].addConnectedNode(nodes[vertex2 - 1]);
        nodes[vertex2 - 1].addConnectedNode(nodes[vertex1 - 1]);
    }
}