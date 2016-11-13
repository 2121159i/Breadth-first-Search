import java.util.Queue;
import java.util.LinkedList;


/**
 class to represent an undirected graph using adjacency lists
 */
public class Graph {

	private Vertex[] vertices; // the (array of) vertices
	private int numVertices = 0; // number of vertices

	// possibly other fields representing properties of the graph

	/**
	 creates a new instance of Graph with n vertices
	*/
	public Graph(int n) {
		numVertices = n;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++)
			vertices[i] = new Vertex(i,"");
	}

	public int size() {
		return numVertices;
	}

	public Vertex getVertex(int i) {
		return vertices[i];
	}

	public void setVertex(int i) {
		vertices[i] = new Vertex(i,"");
	}
	

	/**
	 visit vertex v, with predecessor index p,
	 during a depth first traversal of the graph
	 */
	private void Visit(Vertex v, int p) {
		v.setVisited(true);
		v.setPredecessor(p);
		LinkedList<AdjListNode> L = v.getAdjList();
		for (AdjListNode node : L) {
			int n = node.getVertexNumber();
			if (!vertices[n].getVisited()) {
				Visit(vertices[n], v.getIndex());
			}
		}
	}

	/**
     carry out a depth first search/traversal of the graph
	 */
	public void dfs() {
		for (Vertex v : vertices)
			v.setVisited(false);
		for (Vertex v : vertices)
			if (!v.getVisited())
				Visit(v, -1);
	}

	/**
	 carry out a breadth first search/traversal of the graph
	 */
	public void bfs() {
		
		for (Vertex v : vertices) v.setVisited(false); // initialise (all vertices unvisted)
  		LinkedList<Vertex> queue = new LinkedList<Vertex>();
  		
		for (Vertex v : vertices) { // go through vertices of the graph
    		if (!v.getVisited()) { // if vertex not visited then visit the vertex
      			v.setVisited(true);
      			v.setPredecessor(v.getIndex()); // v was initial/starting vertex
      			queue.add(v); // add to queue for processing
      			while (!queue.isEmpty()) {
        			Vertex u = queue.remove(); // get next vertex to process
					LinkedList<AdjListNode> list = u.getAdjList(); // get adjacency list of the vertex
        			for (AdjListNode node : list) {
						Vertex w = vertices[node.getVertexNumber()];
						if (!w.getVisited()) { // if vertex has not been visited
							w.setVisited(true);
							w.setPredecessor(u.getIndex()); // w was found using u so this is the predecessor
							queue.add(w); // add to queue for processing
						}
					}
				}
			}
		}
	}
		
	public Vertex backTrackAlgorithm(String startWord, String endWord, int startWordPosition, int endWordPosition) {
	
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		queue.add(this.getVertex(startWordPosition));
		vertices[startWordPosition].setVisited(true);
		
		return findShortestPath(queue, endWord);
		
		
	}
	
	public Vertex findShortestPath(Queue<Vertex> container, String endWord){
		while (!container.isEmpty()){
			Vertex stepperVertex = container.remove();
			
			for (AdjListNode node: stepperVertex.getAdjList()){
				processAdjacentNode(node, stepperVertex, container);
				
				if (stepperVertex.getWord().equals(endWord))
					return stepperVertex;
			}
			
		}
		return null;
		
	}
	
	public void processAdjacentNode(AdjListNode node, Vertex stepperVertex, Queue<Vertex> container){
		Vertex adjVertex = vertices[node.getVertexNumber()];	
		
		if (!adjVertex.getVisited()){
			adjVertex.setVisited(true);
			adjVertex.setPredecessor(stepperVertex.getIndex());
			container.add(adjVertex);
		}
		
	}

}