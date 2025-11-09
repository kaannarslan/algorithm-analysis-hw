import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph Implementation with adjacency list
 * @author Kaan Arslan
 */
class Graph {
    int vertexCount;
    Map<String, Vertex> vertexMap;
    private MultiwayTree MST;

    /**
     * Creates a graph with given file
     * @param fileName  given file
     * @throws IOException  if given file is invalid
     */
    public Graph(String fileName) throws IOException
    {
        readFile(fileName);
    }

    /**
     * Read vertex ids from file and fill the adjacency lists.
     * @param fileName given file
     * @throws IOException if given file is invalid
     */
    private void readFile(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        vertexCount = Integer.parseInt(br.readLine().trim());

         vertexMap= new HashMap<>();

        //Reads vertices and put them into vertex map.
        for(int i = 0 ; i < vertexCount ; i++)
        {
            String vertexId = br.readLine().trim();
            Vertex vertex = new Vertex(vertexId);
            vertexMap.put(vertexId, vertex);
        }

        int edgeCount = Integer.parseInt(br.readLine().trim());

        //Reads edges and adds them to adjacency lists
        for (int i = 0 ; i < edgeCount; i++)
        {
            String[] edgeData = br.readLine().trim().split(" ");
            Vertex u = vertexMap.get(edgeData[0]);
            Vertex v = vertexMap.get(edgeData[1]);
            float weight = (float) Double.parseDouble(edgeData[2]);
            u.addAdj(new Edge(u, v, weight));
            v.addAdj(new Edge(v, u, weight));
        }

        br.close();
    }

    /**
     * Runs Prim's Algorithm and constructs the MST
     * @param startId The starting vertex ID
     */
    public void prim(String startId)
    {
        if (!vertexMap.containsKey(startId)) {
            System.out.println("Invalid Start Vertex");
            return;
        }

        MinHeap heap = new MinHeap();
        Vertex start = vertexMap.get(startId);
        start.setKey(0);

        for (Vertex v : vertexMap.values())
            heap.insert(v);

        while (!heap.isEmpty())
        {
            Vertex u = heap.extractMin();

            for (Edge e : u.getAdjList())
            {
                Vertex v = e.getDestination();
                if (heap.vertexPositions.containsKey(v) && e.getWeight() < v.getKey())
                {
                    v.setPred(u);
                    v.setKey(e.getWeight());
                    heap.decreaseKey(v, e.getWeight());
                }
            }
        }
        MST = new MultiwayTree();
        MST.constructMST(vertexMap);


    }

    public Map<String, Vertex> getVertexMap()
    {
        return vertexMap;
    }

    /**
     * Prints the MST for first time
     */
    public void  printMST(String rootId)
    {
        Vertex rootVertex = vertexMap.get(rootId);
        if (rootVertex != null)
        {
            MST.printMST(rootVertex);
        }
    }
}
