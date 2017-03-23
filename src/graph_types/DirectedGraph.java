package graph_types;

import graph_elements.*;

public class DirectedGraph extends Graph {
	
	//Fields
	private int maxIndegree, minIndegree, maxOutdegree, minOutdegree;
	
	//Constructors
	public DirectedGraph( DirectedGraphVertex[] VERTICES, Arc[] ARCS ){
		
		super(VERTICES,ARCS);
		setMaxMinDegrees();
		setRegular();
		setComplete();
	}
	
	public DirectedGraph( DirectedGraphVertex[] VERTICES, boolean[][] ADJACENCY_MATRIX ){
		
		super(VERTICES, ADJACENCY_MATRIX);
		setMaxMinDegrees();
		setRegular();
		setComplete();
	}
	
	//Methods
	protected void setDensity(){
		density = ((double)this.getSize())/((double)(this.getOrder()*(this.getOrder()-1)));
	}
	
	private void setMaxMinDegrees(){
		
		int inmax = 0, inmin = getVertices().length, outmax = 0, outmin = getVertices().length;
		
		for(Vertex X: getVertices()){
			
			if( ((DirectedGraphVertex) X).getIndegree() > inmax )
				inmax = ((DirectedGraphVertex) X).getIndegree();
			else if( ((DirectedGraphVertex) X).getIndegree() < inmin )
				inmin = ((DirectedGraphVertex) X).getIndegree();
			
			if( ((DirectedGraphVertex) X).getOutdegree() > outmax )
				outmax = ((DirectedGraphVertex) X).getOutdegree();
			else if( ((DirectedGraphVertex) X).getOutdegree() < outmin )
				outmin = ((DirectedGraphVertex) X).getOutdegree();
		}
		
		maxIndegree = inmax;
		minIndegree = inmin;
		maxOutdegree = outmax;
		minOutdegree = outmin;
	}
	
	@Override
	public void setVerticesNeighbourhoods(){
		for( Vertex v: this.getVertices() ){
			((DirectedGraphVertex) v).setIndegree(IncidenceMatrix);
			((DirectedGraphVertex) v).setOutdegree(IncidenceMatrix);
		}
		super.setVerticesNeighbourhoods();
	}
	
	@Override
	public void setRegular() {
		regular = ( maxIndegree == minIndegree && maxOutdegree == minOutdegree && maxIndegree == maxOutdegree );
	}
	
	@Override
	public void setComplete(){
		complete = ( maxIndegree == minIndegree && maxOutdegree == minOutdegree && maxIndegree == maxOutdegree && maxIndegree == this.getOrder() - 1 );
	}
	
	
	@Override
	public void buildAdjacencyMatrix() {
		AdjacencyMatrix = new boolean[getVertices().length][getVertices().length];
		for(Adjacency X: getAdjacencies()){
			AdjacencyMatrix[X.getIncidentVertices()[0].getIndex()][X.getIncidentVertices()[1].getIndex()] = true;
		}
	}
	
	@Override
	public void buildIncidenceMatrix() {
		IncidenceMatrix = new byte[getVertices().length][getAdjacencies().length];
		for( Adjacency X: getAdjacencies() ){
			IncidenceMatrix[X.getIncidentVertices()[0].getIndex()][X.getIndex()] = -1;
			IncidenceMatrix[X.getIncidentVertices()[1].getIndex()][X.getIndex()] = 1;
		}
	}
	
	@Override
	public void printInfo(){
		
		super.printInfo();
		
		System.out.printf("\nMaximum Indegree: %d\nMinimum Indegree: %d\n", this.maxIndegree, this.minIndegree);
		System.out.printf("\nMaximum Outdegree: %d\nMinimum Outegree: %d\n", this.maxOutdegree, this.minOutdegree);
	}

	@Override
	public Graph complement() {
		DirectedGraphVertex[] V = new DirectedGraphVertex[this.getVertices().length];
		boolean[][] ADJ_MATRIX = new boolean[V.length][V.length]; 
		for(int i=0; i < V.length; i++){
			for(int j=0; j < V.length; j++)
				if( i != j )
				ADJ_MATRIX[i][j] = !this.getAdjacencyMatrix()[i][j];
			V[i] = new DirectedGraphVertex(this.getVertices()[i].getLabel());
		}
		return new DirectedGraph(V,ADJ_MATRIX);
	}

	@Override
	public void add_Vertices(String[] LABELS) {
		super.add_Vertices(LABELS);
		setMaxMinDegrees();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void add_Adjacencies(String RELATED_VERTICES_LABELS) {
		super.add_Adjacencies(RELATED_VERTICES_LABELS);
		setMaxMinDegrees();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void add_Elements(String[] LABELS, String RELATED_VERTICES_LABELS) {
		super.add_Elements(LABELS,RELATED_VERTICES_LABELS);
		setMaxMinDegrees();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void remove_Vertices(String[] LABELS) {
		super.remove_Vertices(LABELS);
		setMaxMinDegrees();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void remove_Adjacencies(String RELATED_VERTICES_LABELS) {
		super.remove_Adjacencies(RELATED_VERTICES_LABELS);
		setMaxMinDegrees();
		this.setRegular();
		this.setComplete();
	}

	@Override
	public void remove_Elements(String[] LABELS, String RELATED_VERTICES_LABELS) {
		this.remove_Vertices(LABELS);
		this.remove_Adjacencies(RELATED_VERTICES_LABELS);
	}
}