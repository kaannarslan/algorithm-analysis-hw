Author : Kaan Arslan

How to Compile and Run:

	- First, make sure that the .txt files are in the same directory as the .java files.

	- Then open a terminal and navigate to the project directory.

	- Compile all java files using the following command:

			javac *.java

	- Run the program with the input file:
			
			java Main [filename]
	
	- If filename is replaced by the name of a file that is not in the directory, the following output is shown:

			Error reading file: [invalid filename]
	
Known Bugs and Limitations:
	
	- The program assumes the input file follows the correct format.
	
	- The program does not handle graphs that are not connected.
	
	- After the Prim's Algorithm, the MST is changed without any problem with insert-edge and decrease weight methods. However the pred variable held in the Vertex Class is not changed. Because, it is only used in Prim's Algorithm.	

	- Floating-point precision issues may cause minor inconsistencies in weight comparisons.

File Directory:

	- Main.java: Entry point of the program, handles command-line arguments and directives.
	- Graph.java: Represents the graph using an adjacency list.
	- Vertex.java: Defines the vertex structure.
	- Edge.java: Represents weighted edges in the graph.
	- MinHeap.java: Implements a minimum priority queue for Prim's algorithm.
	- MSTNode.java: Represents a node in the multiway tree.
	- MultiwayTree.java: Stores the MST structure and provides operations such as evert and insert edge.

Note:
    This project was developed as an assignment of the Algorithm Analysis course (code 331) at TOBB University of Economics and Technology during the Spring semester of 2024–2025.
    It received a grade of 100/100.
