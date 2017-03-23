package graph_elements;

public class DirectedGraphVertex extends Vertex {
	
	//Fields
	private int indegree, outdegree;
	
	//Constructors
	public DirectedGraphVertex( String LABEL ){
		super(LABEL);
	}
	
	//Methods
	public void setIndegree(byte[][] INCIDENCE_MATRIX){
		int temp_indegree = 0;
		for(int i=0; i < INCIDENCE_MATRIX[this.getIndex()].length; i++)
			if( INCIDENCE_MATRIX[this.getIndex()][i] == 1 )
				temp_indegree++;
		indegree = temp_indegree;
	}
	
	public int getIndegree(){
		return indegree;
	}
	
	public void setOutdegree(byte[][] INCIDENCE_MATRIX){
		int temp_outdegree = 0;
		for(int i=0; i < INCIDENCE_MATRIX[this.getIndex()].length; i++)
			if( INCIDENCE_MATRIX[this.getIndex()][i] == -1 )
				temp_outdegree++;
		outdegree = temp_outdegree;
	}
	
	public int getOutdegree(){
		return outdegree;
	}
	
	@Override
	public void setAdjacencyList(Vertex[] VERTICES, boolean[][] ADJACENCY_MATRIX){
		
		int n = 0;
		AdjacencyList = new Vertex[outdegree];
		
		for(int i=0; i < VERTICES.length; i++)
			if( ADJACENCY_MATRIX[this.getIndex()][i] )
				AdjacencyList[n++] = VERTICES[i];
	}
}