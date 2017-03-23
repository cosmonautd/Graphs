package graph_elements;

public class UndirectedGraphVertex extends Vertex {
	
	//Fields
	private int degree;
	
	//Constructors
	public UndirectedGraphVertex( String LABEL ){
		super(LABEL);
	}
	
	//Methods
	public void setDegree(boolean[][] ADJACENCY_MATRIX){
		int temp_degree = 0;
		for(int i=0; i < ADJACENCY_MATRIX[this.getIndex()].length; i++)
			if( ADJACENCY_MATRIX[this.getIndex()][i] )
				temp_degree++;
		degree = temp_degree;
	}
	
	public int getDegree(){
		return degree;
	}
	
	@Override
	public void setAdjacencyList(Vertex[] VERTICES, boolean[][] ADJACENCY_MATRIX){
		
		int n = 0;
		AdjacencyList = new Vertex[degree];
		
		for(int i=0; i < VERTICES.length; i++)
			if( ADJACENCY_MATRIX[this.getIndex()][i] )
				AdjacencyList[n++] = VERTICES[i];
	}
}