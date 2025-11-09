/**
 * MSTNode class for Multiway Tree class.
 * @author Kaan Arslan
 */
public class MSTNode {

    private Vertex vertex;
    private MSTNode parent;
    private MSTNode preSibling;
    private MSTNode firstChild;
    private MSTNode nextSibling;

    /**
     * Creates MSTNode with given vertex.
     * @param vertex
     */
    public MSTNode(Vertex vertex)
    {
        this.vertex = vertex;
        this.parent = null;
        this.preSibling = null;
        this.firstChild = null;
        this.nextSibling =null;
    }

    //Getters
    public MSTNode getNextSibling() {return nextSibling;}
    public MSTNode getFirstChild() {return firstChild;}
    public MSTNode getPreSibling() {return preSibling;}
    public MSTNode getParent() {return parent;}
    public Vertex getVertex() {return vertex;}

    //Setters
    public void setVertex(Vertex vertex) {this.vertex = vertex;}
    public void setParent(MSTNode parent) {this.parent = parent;}
    public void setPreSibling(MSTNode preSibling) {this.preSibling = preSibling;}
    public void setFirstChild(MSTNode firstChild) {this.firstChild = firstChild;}
    public void setNextSibling(MSTNode nextSibling) {this.nextSibling = nextSibling;}

    public void printTree(String prefix) {
        System.out.println(prefix + vertex.getId());
        if (firstChild != null) firstChild.printTree(prefix + ". ");
        if (nextSibling != null) nextSibling.printTree(prefix);
    }

    /**
     * Add new child to this node alphabetically
     * @param child
     */
    public void addChildAlphabetically(MSTNode child)
    {
        //If there is no first child, adds.
        if (firstChild == null)
            firstChild = child;

        //If this node has first child:
        else
        {
            MSTNode pre = null;
            MSTNode suc = firstChild;

            //Finds true location in alphabetical order.
            while (suc != null && suc.getVertex().getId().compareTo(child.getVertex().getId()) < 0)
            {
                pre = suc;
                suc = suc.getNextSibling();
            }

            //If new added child's id is less than first child alphabetically, adds new child as first child.
            if (pre == null)
            {
                child.setNextSibling(firstChild);
                firstChild.setPreSibling(child);
                firstChild = child;
            }
            else
            {
                pre.setNextSibling(child);
                child.setNextSibling(suc);
                child.setPreSibling(pre);
                if (suc != null)
                    suc.setPreSibling(child);
            }
        }
    }

    @Override
    public String toString() {

        String parentID = null;
        String preSiblingID = null;
        String nextSiblingID = null;
        String firstChildID = null;

        if (parent != null)
            parentID = parent.getVertex().getId();
        if (preSibling != null)
            preSiblingID = preSibling.getVertex().getId();
        if (nextSibling != null)
            nextSiblingID = nextSibling.getVertex().getId();
        if (firstChild != null)
            firstChildID = firstChild.getVertex().getId();

        return "MSTNode{" +
                "vertex=" + vertex.getId() +
                ", parent=" + parentID+
                ", preSibling=" + preSiblingID +
                ", firstChild=" + firstChildID +
                ", nextSibling=" + nextSiblingID +
                '}';
    }
}
