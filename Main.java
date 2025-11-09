import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1)
        {
            System.out.println("Usage: java main <filename>");
            return;
        }

        String fileName = args[0];

        try {
            Graph graph = new Graph(fileName);

            String starterVertex = graph.getVertexMap().keySet().iterator().next();         //First given vertex
            graph.prim(starterVertex);                                                      //Prim was done and pred values of vertex objects were assigned.

            MultiwayTree MST = new MultiwayTree();                                          //New MST object initialized
            MST.constructMST(graph.getVertexMap());                                         //The vertices were passed to the construct method and the MST was designed according to the pred values.

            //Directives:
            processDirectives(fileName, graph, MST);
        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + fileName);
        }
    }
    private static void processDirectives(String fileName, Graph graph, MultiwayTree MST)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            boolean readingDirectives = false;

            while ((line = br.readLine()) != null)
            {
                if (!readingDirectives)
                {
                    if (line.matches("\\D.*"))
                    {
                        readingDirectives = true;
                    }
                    else
                    {
                        continue;
                    }
                }

                String[] tokens = line.split(" ");
                //System.out.println("Directive-----------------> " + line);
                switch (tokens[0]) {
                    case "print-mst":
                        Vertex root = graph.getVertexMap().get(tokens[1]);      //root vertex
                        if (root != null) {
                           MST.printMST(root);
                        } else {
                            System.out.println("Invalid Operation");
                        }
                        break;
                    case "path":
                        Vertex u = graph.getVertexMap().get(tokens[1]);
                        Vertex v = graph.getVertexMap().get(tokens[2]);
                        if (u != null && v != null)
                        {
                            MST.path(u, v);
                        }
                        else
                        {
                            System.out.println("Invalid Operation");
                        }
                        break;
                    case "insert-edge":
                        Vertex v1 = graph.getVertexMap().get(tokens[1]);
                        Vertex v2 = graph.getVertexMap().get(tokens[2]);
                        float w = Float.parseFloat(tokens[3]);

                        if (v1 != null && v2 != null)
                        {
                            MST.insertEdge(v1, v2, w);
                        }
                        else
                        {
                            System.out.println("Invalid Operation");
                        }
                        break;
                    case "decrease-weight":
                        Vertex a = graph.getVertexMap().get(tokens[1]);
                        Vertex b = graph.getVertexMap().get(tokens[2]);
                        float value = Float.parseFloat(tokens[3]);

                        if (a != null && b != null)
                        {
                           MST.decreaseWeight(a, b, value);
                        }
                        else
                        {
                            System.out.println("Invalid Operation");
                        }
                        break;
                    case "quit":
                        return;
                }

            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading directives from file");
        }
    }
}
