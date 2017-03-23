package graph_elements;

public class Arc extends Adjacency {
	
	//Constructors
	public Arc( Vertex STARTPOINT, Vertex ENDPOINT, double WEIGHT ){
			
		super(STARTPOINT, ENDPOINT, WEIGHT);
	}
			
	public Arc( Vertex STARTPOINT, Vertex ENDPOINT ){
				
		super(STARTPOINT, ENDPOINT, 1);
	}
	
	public Arc( Vertex STARTPOINT, Vertex ENDPOINT, double WEIGHT, String LABEL ){
		
		super(STARTPOINT, ENDPOINT, WEIGHT, LABEL);
	}
			
	public Arc( Vertex STARTPOINT, Vertex ENDPOINT, String LABEL ){
				
		super(STARTPOINT, ENDPOINT, 1, LABEL);
	}
	
}