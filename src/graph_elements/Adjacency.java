package graph_elements;

public abstract class Adjacency {
	
	//Fields
	protected int index;
	protected String label;
	protected double weight;
	protected Vertex[] IncidentVertices;
	
	//Constructors
	public Adjacency( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, double WEIGHT ){
			
		setWeight(WEIGHT);
		setIncidentVertices(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2);
	}
			
	public Adjacency( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2 ){
				
		setWeight(1);
		setIncidentVertices(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2);
	}
	
	public Adjacency( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, double WEIGHT, String LABEL ){
		
		setWeight(WEIGHT);
		setIncidentVertices(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2);
		setLabel(LABEL);
	}
	
	public Adjacency( Vertex INCIDENT_VERTEX_1, Vertex INCIDENT_VERTEX_2, String LABEL ){
		
		setWeight(1);
		setIncidentVertices(INCIDENT_VERTEX_1, INCIDENT_VERTEX_2);
		setLabel(LABEL);
	}
	
	//Methods
	public void setIndex( int INDEX ){
		if(INDEX >= 0)
			index = INDEX;
	}
	
	public int getIndex(){
		return index;
	}
	
	private void setLabel(String LABEL){
		label = LABEL;
	}
		
	public String getLabel(){
		return label;
	}
	
	protected void setWeight( double WEIGHT ){
		if(WEIGHT > 0)
			weight = WEIGHT;
		else
			weight = 0;
	}
		
	public double getWeight(){
		return weight;
	}
		
	private void setIncidentVertices( Vertex VERTEX_1, Vertex VERTEX_2 ){
		
		IncidentVertices = new Vertex[2];
		
		IncidentVertices[0] = VERTEX_1;
		IncidentVertices[1] = VERTEX_2;
	}
		
	public Vertex[] getIncidentVertices(){
		return IncidentVertices;
	}
		
	public String stringIncidentVertices(){
		return String.format("%s-%s", IncidentVertices[0].getLabel(), IncidentVertices[1].getLabel());
	}
}