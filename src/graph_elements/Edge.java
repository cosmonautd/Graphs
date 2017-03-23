package graph_elements;

public class Edge extends Adjacency{
	
	//Constructors
	public Edge( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, double WEIGHT ){
			
		super(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2, WEIGHT);
	}
			
	public Edge( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2 ){
				
		super(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2, 1);
	}
	
	public Edge( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, double WEIGHT, String LABEL ){
		
		super(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2, WEIGHT, LABEL);
	}
			
	public Edge( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, String LABEL ){
				
		super(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2, 1, LABEL);
	}
}