package graph_types;

import graph_elements.*;

public class UndirectedGraph extends Graph {
	
	//Fields
	private int maxDegree, minDegree;
	
	//Constructors
	public UndirectedGraph( UndirectedGraphVertex[] VERTICES, Edge[] EDGES ){
			
		super(VERTICES, EDGES);
		setMaxMinDegree();
		setRegular();
		setComplete();
	}
	
	public UndirectedGraph( UndirectedGraphVertex[] VERTICES, boolean[][] ADJACENCY_MATRIX ){
		
		super(VERTICES, ADJACENCY_MATRIX);
		setMaxMinDegree();
		setRegular();
		setComplete();
	}
	
	//Methods
	protected void setDensity(){
		density = (2*(double)this.getSize())/((double)(this.getOrder()*(this.getOrder()-1)));
	}
	
	private void setMaxMinDegree(){
		
		int max = 0, min = getVertices().length;
		
		for(Vertex X: getVertices())
			if( ((UndirectedGraphVertex) X).getDegree() > max )
				max = ((UndirectedGraphVertex) X).getDegree();
			else if( ((UndirectedGraphVertex) X).getDegree() < min )
				min = ((UndirectedGraphVertex) X).getDegree();
		
		maxDegree = max;
		minDegree = min;
	}
	
	public int get_maxDegree(){
		return maxDegree;
	}
	
	public int get_minDegree(){
		return minDegree;
	}
	
	@Override
	public void setVerticesNeighbourhoods(){
		for( Vertex v: this.getVertices() )
			((UndirectedGraphVertex) v).setDegree(AdjacencyMatrix);
		super.setVerticesNeighbourhoods();
	}
	
	@Override
	public void setRegular(){
		regular = (maxDegree == minDegree);
	}
	
	@Override
	public void setComplete(){
		complete = (maxDegree == minDegree && maxDegree == this.getOrder() - 1 );
	}
	
	@Override
	public void buildAdjacencyMatrix() {
		AdjacencyMatrix = new boolean[getVertices().length][getVertices().length];
		for(Adjacency X: getAdjacencies()){
			AdjacencyMatrix[X.getIncidentVertices()[0].getIndex()][X.getIncidentVertices()[1].getIndex()] = true;
			AdjacencyMatrix[X.getIncidentVertices()[1].getIndex()][X.getIncidentVertices()[0].getIndex()] = true;
		}
	}
	
	@Override
	public void buildIncidenceMatrix() {
		IncidenceMatrix = new byte[getVertices().length][getAdjacencies().length];
		for( Adjacency X: getAdjacencies() ){
			IncidenceMatrix[X.getIncidentVertices()[0].getIndex()][X.getIndex()] = 1;
			IncidenceMatrix[X.getIncidentVertices()[1].getIndex()][X.getIndex()] = 1;
		}
	}
	
	@Override
	public void printInfo(){
		
		super.printInfo();
		
		System.out.println("\nVertices' Neighbourhoods: ");
		this.printVerticesNeighbourhoods();
		
		System.out.printf("\nMaximum degree: %d\nMinimum degree: %d\n", this.get_maxDegree(), this.get_minDegree());
	}

	@Override
	public Graph complement() {
		UndirectedGraphVertex[] V = new UndirectedGraphVertex[this.getVertices().length];
		boolean[][] ADJ_MATRIX = new boolean[V.length][V.length]; 
		for(int i=0; i < V.length; i++){
			for(int j=i+1; j < V.length; j++)
				ADJ_MATRIX[i][j] = !this.getAdjacencyMatrix()[i][j];
			V[i] = new UndirectedGraphVertex(this.getVertices()[i].getLabel());
		}
		return new UndirectedGraph(V,ADJ_MATRIX);
	}

	@Override
	public void add_Vertices(String[] LABELS) {
		super.add_Vertices(LABELS);
		this.setMaxMinDegree();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void add_Adjacencies(String RELATED_VERTICES_LABELS) {
		super.add_Adjacencies(RELATED_VERTICES_LABELS);
		this.setMaxMinDegree();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void add_Elements(String[] LABELS, String RELATED_VERTICES_LABELS) {
		super.add_Elements(LABELS,RELATED_VERTICES_LABELS);
		this.setMaxMinDegree();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void remove_Vertices(String[] LABELS) {
		super.remove_Vertices(LABELS);
		this.setMaxMinDegree();
		this.setRegular();
		this.setComplete();
	}
	
	@Override
	public void remove_Adjacencies(String RELATED_VERTICES_LABELS) {
		super.remove_Adjacencies(RELATED_VERTICES_LABELS);
		this.setMaxMinDegree();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void remove_Elements(String[] LABELS, String RELATED_VERTICES_LABELS) {
		this.remove_Vertices(LABELS);
		this.remove_Adjacencies(RELATED_VERTICES_LABELS);
	}
}