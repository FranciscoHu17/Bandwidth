import java.io.*;

public class Graph {
    //private static Node nodes[];
    private static Node allCandidates[];
    private static int nodesMatrix[][];

    /**
     * Runs the program that creates the graph by reading the file and outputting
     * the results
     * 
     * @param args
     */
    public static void main(String args[]) {
        try {
            System.out.println("File 1:");
            File file1 = new File("Test Files/g-p-18-17");
            createGraphFromFile(file1);
            GraphSearch file1Search = new GraphSearch(allCandidates,nodesMatrix);
            int solution[] = new int[nodesMatrix.length];

            for (int i = 0; i < solution.length; i++) {
                solution[i] = -1;
            }
            file1Search.backtrack(solution, -1);
            file1Search.printArray(file1Search.getMinMaxBandwidth());
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
     * Initializes the nodesmatrix[][] variable
     * 
     * @param vertices The amount of vertices in the graph
     */
    public static void initNodes(int vertices) {
        allCandidates = new Node[vertices];
        nodesMatrix = new int[vertices][vertices];
        for (int r = 0; r < vertices; r++) {
            allCandidates[r] = new Node(r + 1);
            for(int c = 0; c < vertices; c++){
                nodesMatrix[r][c] = 0;
            }
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
        nodesMatrix[vertex1 - 1][vertex2 - 1] = 1;
        nodesMatrix[vertex2 - 1][vertex1 - 1] = 1;
        allCandidates[vertex1 - 1].incrementDegree();
        allCandidates[vertex2 - 1].incrementDegree();
    }

    public static void printMatrix(){
        for(int r = 0; r < nodesMatrix.length; r++){
            for(int c = 0; c < nodesMatrix[r].length; c++){
                System.out.print(nodesMatrix[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    

    
}