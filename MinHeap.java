import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MinHeap implementation. This class is used for find the vertex that has minimum key value.
 * Contains insert, decreaseKey and extractMin methods.
 * @author Kaan Arslan
 */
public class MinHeap {
    private List<Vertex> heap;
    Map<Vertex,Integer> vertexPositions;    // For find the index of given vertex at O(1).
    private static final double EPSILON = 1e-9;

    /**
     * Creates MinHeap object.
     */
    public MinHeap()
    {
        heap = new ArrayList<>();
        vertexPositions = new HashMap<>();
    }

    /**
     * Inserts new vertex into the MinHeap
     * @param vertex
     */
    public void insert(Vertex vertex)
    {
        heap.add(vertex);
        int index = heap.size()-1;
        int newIndex = upheap(index);
        vertexPositions.put(vertex,newIndex);

    }

    /**
     * Returns the Vertex that has minimum key value.
     * @return  Vertex
     */
    public Vertex extractMin()
    {
        if (heap.isEmpty()) return null;

        Vertex min = heap.get(0);
        Vertex last =  heap.remove(heap.size() - 1);

        if (!heap.isEmpty())
        {
            heap.set(0, last);
            int newIndex = downheap(0);
            vertexPositions.put(last, newIndex);

        }

        vertexPositions.remove(min);
        return min;
    }

    /**
     * Decreases the key value of the given vertices
     * @param vertex    given vertex
     * @param key   new key value
     */
    public void decreaseKey(Vertex vertex, float key)
    {
        if (!vertexPositions.containsKey(vertex)) return;
        int index = vertexPositions.get(vertex);
        int newIndex = upheap(index);
        vertexPositions.put(vertex, newIndex);

    }

    /**
     * Moves the entry at index j higher. Returns new index.
     * @param j
     * @return int new index
     */
    private int upheap(int j)
    {
        while (j > 0)
        {
            int parent = (j-1)/2;
            if (heap.get(parent).getKey() - heap.get(j).getKey() <= EPSILON)  break;
            swap(parent, j);
            j = parent;
        }
        return  j;
    }

    /**
     * Moves the entry at index j lower. Returns new index.
     * @param j
     * @return int new index
     */
    private int downheap(int j)
    {
        int size = heap.size();
        while (true)
        {
            int left = 2*j+1;
            int right = 2*j+2;
            int smallest = j;

            if (left < size && Double.compare(heap.get(left).getKey(), heap.get(smallest).getKey()) == -1)
            {
                smallest = left;
            }
            if (right < size && Double.compare(heap.get(right).getKey(), heap.get(smallest).getKey()) == -1)
            {
                smallest = right;
            }

            if (smallest == j) break;

            swap(j, smallest);
            j = smallest;
        }
        return j;
    }

    /**
     * Swaps the vertices at indices i and j.
     * @param i
     * @param j
     */
    private void swap(int i, int j)
    {
        Vertex temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        vertexPositions.put(heap.get(i), i);
        vertexPositions.put(heap.get(j), j);
    }

    /**
     * Checks heap is empty or not
     * @return true if heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size()
    {
        return heap.size();
    }
}
