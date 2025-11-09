/**
 * Edge class for using in Graph Implementation
 * @author Kaan Arslan
 */
public class Edge {
    private Vertex source;
    private Vertex destination;
    private float weight;

    /**
     * Creates an edge with given endpoints and double weight value.
     * @param source    From vertex
     * @param destination To vertex
     * @param weight    Weight value
     */
    public Edge(Vertex source, Vertex destination, float weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns weight of edge.
     * @return double weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Returns source vertex of the edge
     * @return Vertex source
     */
    public Vertex getSource()
    {
        return source;
    }

    /**
     * Returns destination vertex of the edge
     * @return Vertex destination
     */
    public  Vertex getDestination()
    {
        return destination;
    }

    /**
     * Sets source vertex
     * @param source
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * Sets weight
     * @param weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Sets destination vertex
     * @param destination
     */
    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
