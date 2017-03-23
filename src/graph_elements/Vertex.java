package graph_elements;

public abstract class Vertex {
	
	//Fields
	private int index;
	private String label;
	protected Vertex[] AdjacencyList;
	
	//Constructors
	public Vertex( String LABEL ){
		setLabel(LABEL);
	}
	
	//Methods
	public void setIndex(int INDEX){
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
	
	public abstract void setAdjacencyList(Vertex[] VERTICES, boolean[][] ADJACENCY_MATRIX);
	
	public Vertex[] getAdjacencyList(){
		return AdjacencyList;
	}
	
	public String stringAdjacencyList(){
		
		String stringAdjacencyList = "";
		
		if( AdjacencyList.length > 0 )
			for(Vertex X: AdjacencyList)
				stringAdjacencyList+=(X.getLabel() + " ");
		else return null;
		
		return stringAdjacencyList;
	}
	
}