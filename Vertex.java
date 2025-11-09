import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Vertex class for using in Graph Implementation
 * @author Kaan Arslan
 */
public class Vertex {
    private String id;
    private List<Edge> adjacencyList;
    private float key;
    private Vertex pred;

    /**
     *Creates a vertex with given id
     * @param id given id parameter
     */
    public Vertex(String id)
    {
        this.id = id;
        adjacencyList = new ArrayList<>();
        key = 10000000;
        pred = null;
    }

    /**
     * Sets key value for Prim's Algorithm
     * @param key
     */
    public void setKey(float key)
    {
        this.key = key;
    }

    /**
     * Sets id of vertex
     * @param id
     */
    public void setID(String id)
    {
        this.id = id;
    }

    /**
     * Setter for pred value.
     * @param vertex
     */
    public void setPred(Vertex vertex)
    {
        this.pred = vertex;
    }

    /**
     * Getter for pred value.
     * @return Vertex pred
     */
    public Vertex getPred()
    {
        return pred;
    }

    /**
     * Returns key of the vertex.
     * @return double key value.
     */
    public double getKey()
    {
        return key;
    }

    /**
     * Returns id of the vertex
     * @return String id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Adds Edges to Vertex's adjacency list
     * @param e given edge
     */
    public void addAdj(Edge e)
    {
        adjacencyList.add(e);
    }

    /**
     * Return the Vertex's Adjacency List
     * @return  List
     */
    public List<Edge> getAdjList()
    {
        return adjacencyList;
    }

    /**
     * Overriding method that shows two vertex equals or not
     * @param o     other vertex
     * @return      true if both of them are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    /**
     * Return hashcode of vertex objects
     * @return  hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
