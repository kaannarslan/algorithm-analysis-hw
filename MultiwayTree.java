import java.util.*;

/**
 * Multiway Tree implementation for demonstrate MST structure.
 * @author Kaan Arslan
 */
public class MultiwayTree {
    private MSTNode root;
    private Map<Vertex, MSTNode> nodeMap;

    /**
     * Creates MultiWay Tree and initialize nodeMap.
     */
    public MultiwayTree()
    {
        this.nodeMap = new HashMap<>();
    }

    /**
     * Constructs minimum spanning tree.
     * @param vertexMap
     */
    public void constructMST(Map<String, Vertex> vertexMap)
    {
        Vertex rootVertex = null;
        for (Vertex v : vertexMap.values())
        {
            MSTNode node = new MSTNode(v);
            nodeMap.put(v,node);
            node.setVertex(v);
        }

        for (Vertex v : vertexMap.values())
        {
            if (v.getPred() == null)        //root
            {
                rootVertex = v;
                break;
            }
        }
        if (rootVertex == null) {
            System.out.println("Error: No root vertex found in MST!");
            return;
        }
        root = nodeMap.get(rootVertex);     //we found root

        for (Vertex v : vertexMap.values())
        {
            if (v.getPred() != null)    // root vertex değilse
            {
                MSTNode parent = nodeMap.get(v.getPred());
                MSTNode child = nodeMap.get(v);
                child.setParent(parent);
                parent.addChildAlphabetically(child);
            }

        }
    }

    /**
     * Finds and prints the path between Vertex u and Vertex v
     * @param u
     * @param v
     */
    public void path(Vertex u, Vertex v)
    {
        System.out.println("Directive-----------------> path " + u.getId() + " " + v.getId());
        if (!nodeMap.containsKey(u) || !nodeMap.containsKey(v))
        {
            System.out.println("Invalid Operation");
            return;
        }
        if (u.equals(v))
        {
            System.out.print(u.getId());
        }

        //prints the returning edge list in reverse
        List<Edge> path = pathEdges(u,v);
        for (int i = path.size()-1 ; i >= 0 ; i--)
        {
            if (i == path.size()-1)
                System.out.print(path.get(i).getDestination()+ ", ");
            System.out.print(path.get(i).getSource());
            if (i != 0)
                System.out.print(", ");
        }
        System.out.println();

/*
        //Evert u
        evert(u);

        //Creates path.
        StringBuilder pathsb = new StringBuilder();
        MSTNode current = nodeMap.get(v);       // Vertex v

        while (current != null)
        {
            pathsb.insert(0, current.getVertex().getId());      // Adds to the beginning every time.
            current = current.getParent();                          //Goes up until root.
            if (current != null)
            {
                pathsb.insert(0,", ");
            }
        }

        System.out.println("\"Directive-----------------> path " + u.getId() + " " + v.getId());
        System.out.println(pathsb.toString());

*/
    }

    /**
     * Find the path between Vertex u and Vertex v, and returns the edge list.
     * @param u from u
     * @param v to v
     * @return
     */
    private List<Edge> pathEdges(Vertex u, Vertex v)
    {
        List<Edge> edges = new ArrayList<>();

        //Evert u
        evert(u);

        MSTNode current = nodeMap.get(v);
        while (current.getParent() != null)
        {
            Vertex parentVertex = current.getParent().getVertex();
            for (Edge e : current.getVertex().getAdjList())
            {
                if (e.getDestination().equals(parentVertex))        //Finds the edge that goes to parent Vertex.
                {
                    edges.add(e);
                    break;
                }
            }
            current = current.getParent();                          //Goes up until root.
        }
        return edges;
    }

    /**
     * Decreases the weight of the edge between u and v.
     * @param u Vertex U
     * @param v Vertex V
     * @param w Weight
     */
    public void decreaseWeight(Vertex u, Vertex v, float w)
    {
        System.out.println("Directive-----------------> decrease-weight " + u.getId() + " " + v.getId() + " " + w);

        //edgeU and edgeV represents same edges in different adjacency lists.

        //Finds edge-U in Adjacency List of U.
        Edge edgeU = null;
        for(Edge e : u.getAdjList())
        {
            if (e.getDestination().equals(v))
            {
                edgeU = e;
                break;
            }
        }

        //Finds edge-V in Adjacency List of V.
        Edge edgeV = null;
        for (Edge e : v.getAdjList())
        {
            if (e.getDestination().equals(u))
            {
                edgeV = e;
                break;
            }
        }

        //If this edge does not exist, this operation is a Invalid Operation
        if (edgeU == null || edgeV == null)
        {
            System.out.println("Invalid Operation");
            return;
        }

        //Sets new weight value.
        edgeU.setWeight(edgeU.getWeight()-w);
        edgeV.setWeight(edgeV.getWeight()-w);

        //Fınds path between u, v
        List<Edge> path = pathEdges(u,v);

        //Finds max weight in path.
        Edge max = path.get(0);
        for (Edge e : path)
        {
            if (Float.compare(e.getWeight(), max.getWeight()) > 0)
                max = e;
        }

        if (Float.compare(edgeV.getWeight(), max.getWeight()) < 0)
        {
            //This part makes "v" the root of its own subTree. It works like evert method.

            cut(max.getSource());

            MSTNode current = nodeMap.get(v);
            MSTNode parent = current.getParent();
            cut(current.getVertex());
            while (parent != null)
            {
                MSTNode grandParent = parent.getParent();
                cut(parent.getVertex());
                link(parent.getVertex(),current.getVertex());
                current = parent;
                parent = grandParent;
            }
            link(v, u);
        }

    }

    /**
     * Inserts new Edge between U and V with w weight
     * @param u Vertex U
     * @param v Vertex V
     * @param w weigh
     */
    public void insertEdge(Vertex u, Vertex v, float w)
    {
        System.out.println("Directive-----------------> insert-edge " + u.getId() + " " + v.getId() + " " + w);

        //Verilen vertexlerden en az biri MST'de yoksa Invalid Operation
        if (!nodeMap.containsKey(u) || !nodeMap.containsKey(v))
        {
            System.out.println("Invalid Operation");
            return;
        }

        //Attempting to insert an edge that already exists cause Invalid Operation
        for (Edge e : u.getAdjList())
        {
            if (e.getDestination().equals(v))
            {
                System.out.println("Invalid Operation");
                return;
            }
        }
        //Adds to the Original Graph
        u.addAdj(new Edge(u,v,w));
        v.addAdj(new Edge(v,u,w));

        //Finds edge that has maximum weight value.
        List<Edge> path = pathEdges(u, v);                              //Finds path between u and v
        Edge max = path.get(0);
        for (Edge e : path)
        {
            if (Float.compare(e.getWeight(), max.getWeight()) > 0)
                max = e;
        }

        //adds new edge
        if (Float.compare(w,max.getWeight()) < 0)
        {
            cut(max.getSource());

            //Added for testing
            //System.out.println("Cut ettiğim vertex: " + max.getSource().getId());

            //This part makes "v" the root of its own subTree. It works like evert method.
            MSTNode current = nodeMap.get(v);
            MSTNode parent = current.getParent();
            cut(current.getVertex());
            while (parent != null)
            {
                MSTNode grandParent = parent.getParent();
                cut(parent.getVertex());
                link(parent.getVertex(),current.getVertex());
                current = parent;
                parent = grandParent;
            }

            link(v, u);
        }
    }

    /**
     * Makes the given vertex root with cut & link methods.
     * @param u given vertex.
     */
    public void evert(Vertex u)
    {
        MSTNode node = nodeMap.get(u);
        if (node == null)
        {
            //For Testing
            /*for (MSTNode n : nodeMap.values())
            {
                System.out.println(n.toString());
            }*/
            System.out.println("Böyle bir vertex bulamadık.");
            return;
        }
        if (node == root)
        {
            //For Testing
            /*for (MSTNode n : nodeMap.values())
            {
                System.out.println(n.toString());
            }*/
            return;
        }

        MSTNode current = node;
        MSTNode parent = current.getParent();

        cut(current.getVertex());

        while (parent != null)
        {
            MSTNode grandParent = parent.getParent();
            cut(parent.getVertex());
            link(parent.getVertex(), current.getVertex());
            current = parent;
            parent = grandParent;
        }

        root = node;


        //For Testing
        /*for (MSTNode n : nodeMap.values())
        {
            System.out.println(n.toString());
        }*/
    }

    /**
     * Cuts the given vertex from the tree
     * @param u given vertex
     */
    private void cut(Vertex u)
    {
        MSTNode node = nodeMap.get(u);

        //If node does not exist or node is root.
        if (node == null || node.getParent() == null)
        {
            return;
        }

        MSTNode parent = node.getParent();

        //If node is first Child
        if (parent.getFirstChild() == node)
        {
            parent.setFirstChild(node.getNextSibling());
            if (node.getNextSibling() !=null)
                node.getNextSibling().setPreSibling(null);
            node.setNextSibling(null);
        }
        //else
        else
        {
            MSTNode preSibling = node.getPreSibling();
            MSTNode nextSibling = node.getNextSibling();
            if (preSibling != null)
                preSibling.setNextSibling(node.getNextSibling());
            if (nextSibling != null)
                nextSibling.setPreSibling(node.getPreSibling());
        }
        node.setParent(null);
        node.setNextSibling(null);
        node.setPreSibling(null);
    }

    /**
     * Links given vertex u and v as v is parent of u.
     * @param u given child vertex
     * @param v given parent vertex
     */
    private void link(Vertex u, Vertex v)
    {
        MSTNode child = nodeMap.get(u);
        MSTNode parent = nodeMap.get(v);

        if (child == null || parent == null)
        {
            System.out.println("Vertexlerden biri yok!");
            return;
        }

        child.setParent(parent);

        //If parent has not any child, sets node as first child
        if (parent.getFirstChild() == null)
        {
            parent.setFirstChild(child);
        }
        else
        {
            parent.addChildAlphabetically(child);
        }
    }

    /**
     * Prints Minimum Spanning Tree
     * @param rootVertex
     */
    public void printMST(Vertex rootVertex)
    {
        MSTNode rootNode =  nodeMap.get(rootVertex);
        if (rootNode != null) {
            evert(rootVertex);
            System.out.println("Directive-----------------> print-mst " + rootVertex.getId());
            rootNode.printTree("");
        } else {
            System.out.println("Error: Root vertex not found in tree!");
        }

    }

}

