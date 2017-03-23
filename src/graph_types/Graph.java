package graph_types;

import graph_elements.*;
import graph_util.*;

public abstract class Graph {
	
	//Fields
	private Vertex[] Vertices;
	private Adjacency[] Adjacencies;
	private int order, size;
	protected double density;
	protected boolean[][] AdjacencyMatrix;
	protected byte[][] IncidenceMatrix;
	protected boolean regular, complete;
	
	//Constructors
	public Graph( UndirectedGraphVertex[] VERTICES, Edge[] ADJACENCIES ){
		
		setVertices(VERTICES);
		setOrder();
		setAdjacencies(ADJACENCIES);
		setSize();
		setDensity();
		setVerticesIndexes();
		setAdjacenciesIndexes();
		buildAdjacencyMatrix();
		buildIncidenceMatrix();
		setVerticesNeighbourhoods();
	}
	
	public Graph( DirectedGraphVertex[] VERTICES, Arc[] ADJACENCIES ){
		
		setVertices(VERTICES);
		setOrder();
		setAdjacencies(ADJACENCIES);
		setSize();
		setDensity();
		setVerticesIndexes();
		setAdjacenciesIndexes();
		buildAdjacencyMatrix();
		buildIncidenceMatrix();
		setVerticesNeighbourhoods();
	}
	
	public Graph( UndirectedGraphVertex[] VERTICES, boolean[][] ADJACENCY_MATRIX ){
		
		setVertices(VERTICES);
		setOrder();
		setVerticesIndexes();
		
		String S = "";
		for(int i=0; i < ADJACENCY_MATRIX.length; i++)
			for(int j=i+1; j < ADJACENCY_MATRIX[i].length; j++)
				if( ADJACENCY_MATRIX[i][j] )
					S+=( Vertices[i].getLabel() + "-" + Vertices[j].getLabel() + " " );
		
		setAdjacencies( Util.readAdjacencies(S, Vertices) );
		setSize();
		setDensity();
		setAdjacenciesIndexes();
		buildAdjacencyMatrix();
		buildIncidenceMatrix();
		setVerticesNeighbourhoods();
	}
	
	public Graph( DirectedGraphVertex[] VERTICES, boolean[][] ADJACENCY_MATRIX ){
		
		setVertices(VERTICES);
		setOrder();
		setVerticesIndexes();
		
		String S = "";
		for(int i=0; i < ADJACENCY_MATRIX.length; i++)
			for(int j=0; j < ADJACENCY_MATRIX[i].length; j++)
				if( ADJACENCY_MATRIX[i][j] && i!=j )
					S+=( Vertices[i].getLabel() + "-" + Vertices[j].getLabel() + " " );
		
		setAdjacencies( Util.readAdjacencies(S,Vertices) );
		setSize();
		setDensity();
		setAdjacenciesIndexes();
		buildAdjacencyMatrix();
		buildIncidenceMatrix();
		setVerticesNeighbourhoods();
	}
	
	//Methods
	public abstract void buildAdjacencyMatrix();
	public abstract void buildIncidenceMatrix();
	
	private void setVertices( Vertex[] VERTICES ){
		Vertices = VERTICES;
	}
	
	public Vertex[] getVertices(){
		return Vertices;
	}
	
	private void setVerticesIndexes(){
		
		for(int i=0; i < Vertices.length; i++)
			Vertices[i].setIndex(i);
	}
	
	private void setAdjacencies( Adjacency[] ADJACENCIES ){
		Adjacencies = ADJACENCIES;
	}
	
	private void setAdjacencies( boolean[][] ADJACENCY_MATRIX ){
		String S = "";
		for(int i=0; i < ADJACENCY_MATRIX.length; i++)
			for(int j=i+1; j < ADJACENCY_MATRIX[i].length; j++)
				if( ADJACENCY_MATRIX[i][j] )
					S+=( Vertices[i].getLabel() + "-" + Vertices[j].getLabel() + " " );
		
		setAdjacencies( Util.readAdjacencies(S,Vertices) );
	}
	
	public Adjacency[] getAdjacencies(){
		return Adjacencies;
	}
	
	private void setAdjacenciesIndexes(){
		
		for(int i=0; i < Adjacencies.length; i++) {
			Adjacencies[i].setIndex(i);
		}
	}
	
	private void setOrder(){
		order = Vertices.length;
	}
	
	public int getOrder(){
		return order;
	}
	
	private void setSize(){
		size = Adjacencies.length;
	}
	
	public int getSize(){
		return size;
	}
	
	protected abstract void setDensity();
	
	public double getDensity(){
		return density;
	}
	
	public boolean[][] getAdjacencyMatrix(){
		return AdjacencyMatrix;
	}
	
	public void printAdjacencyMatrix(){
		if(AdjacencyMatrix.length != 0 && AdjacencyMatrix[0].length != 0){
			System.out.println("\n");
			for(int i=0; i < AdjacencyMatrix.length; i++){
				for(int j=0; j < AdjacencyMatrix[i].length; j++)
					if(AdjacencyMatrix[i][j])
						System.out.print(" " + 1 + " ");
				    else System.out.print(" " + 0 + " ");
			    System.out.println();
		    }
		}
		else System.out.println("null");
	}
	
	public byte[][] getIncidenceMatrix(){
		return IncidenceMatrix;
	}
	
	public void printIncidenceMatrix(){
		if(IncidenceMatrix.length != 0 && IncidenceMatrix[0].length != 0){
			System.out.println("\n");
			for(int i=0; i < IncidenceMatrix.length; i++){
				for(int j=0; j < IncidenceMatrix[i].length; j++)
					System.out.printf("%2d ", IncidenceMatrix[i][j]);
				System.out.println();
			}
		}
		else System.out.println("null");
	}
	
	public void setVerticesNeighbourhoods(){
		for(Vertex X: Vertices){
			X.setAdjacencyList(Vertices,AdjacencyMatrix);
		}
	}
	
	public void printVerticesNeighbourhoods(){
		if(Vertices.length > 0){
			System.out.println();
			for(Vertex X: Vertices){
				System.out.print(" " + X.getLabel() + ":" );
				for(Vertex Y: X.getAdjacencyList())
					System.out.print( " " + Y.getLabel() );
				System.out.println();
				}
		}
		else System.out.println("null");
	}
	
	public abstract void setRegular();
	
	public boolean isRegular(){
		return regular;
	}
	
	public abstract void setComplete();
	
	public boolean isComplete(){
		return complete;
	}
	
	public String stringVertices(){
		
		String stringVertices = "";
		
		if( Vertices.length > 0 )
			for(Vertex v: Vertices)
				stringVertices+=(v.getLabel() + " ");
		else return null;
		
		return stringVertices;
	}
	
	public String stringAdjacencies(){
		
		String stringAdjacencies = "";
		
		if( Adjacencies.length > 0 )
			for(Adjacency a: Adjacencies)
				stringAdjacencies+=(a.stringIncidentVertices() + " ");
		else return null;
		
		return stringAdjacencies;
	}
	
	public void printInfo(){
		System.out.print("\nVertices: " + stringVertices());
		
		System.out.println();
		
		System.out.print("Adjacencies: " + stringAdjacencies());
		
		System.out.printf("\n\nOrder: %d\nSize: %d\n", order, size);
		
		System.out.print("\nIncidence Matrix: ");
		this.printIncidenceMatrix();
		
		System.out.print("\nAdjacency Matrix: ");
		this.printAdjacencyMatrix();
		
		System.out.println("\nRegular: " + this.isRegular() + "\nComplete: " + this.isComplete());

        System.out.println("\nDensity: " + this.getDensity());
	}
	
	public boolean contains(Vertex X){
		for(Vertex Y: Vertices)
			if( X.getLabel() == Y.getLabel() )
				return true;
		return false;
	}
	
	public boolean contains(Adjacency X){
		for(Adjacency Y: Adjacencies)
			if( X.stringIncidentVertices().equals( Y.stringIncidentVertices() ) )
				return true;
		return false;
	}
	
	public boolean subgraphOf(Graph X){
		
		for(Vertex Y: Vertices)
			if( ! X.contains(Y) )
				return false;
		
		for(Adjacency Z: Adjacencies)
			if( ! X.contains(Z) )
				return false;
		
		return true;
	}
	
	public boolean supergraphOf(Graph X){
		return X.subgraphOf(this);
	}
	
	public boolean spanningSubgraphOf(Graph X){
		return ( this.subgraphOf(X) && Vertices.length == X.Vertices.length );
	}
	
	public boolean inducedSubgraphOf(Graph X){
		
		Adjacency[] AD = Util.AdjacenciesDifference(X,this); 
		
		if( this.subgraphOf(X) ){
			for( Adjacency A: AD )
				if( this.contains( A.getIncidentVertices()[0] ) && this.contains( A.getIncidentVertices()[1] ) )
					return false;
			return true;
		}
		
		return false;
	}
	
	public abstract Graph complement();
	
	public void add_Vertices(String[] LABELS){
		Vertex[] newVertices = Util.expand(Vertices,LABELS.length);
		if(this instanceof UndirectedGraph){
			for(int i=0; i < LABELS.length; i++)
			    newVertices[i+Vertices.length] = new UndirectedGraphVertex(LABELS[i]);
			this.setVertices((UndirectedGraphVertex[]) newVertices);
			
		} else if(this instanceof DirectedGraph){
			for(int i=0; i < LABELS.length; i++)
			    newVertices[i+Vertices.length] = new DirectedGraphVertex(LABELS[i]);
			this.setVertices((DirectedGraphVertex[]) newVertices);
		}
		                                       
		this.setOrder();
		this.setVerticesIndexes();
		this.buildAdjacencyMatrix();
		this.buildIncidenceMatrix();
		this.setVerticesNeighbourhoods();
	}
	
	public void add_Adjacencies(String RELATED_VERTICES_LABELS){
		String[] Labels = RELATED_VERTICES_LABELS.split("[ -]+");
		Adjacency[] newAdjacencies = Util.expand(Adjacencies,Labels.length/2);
		for(int i=0; i < Labels.length/2; i++)
			for(int j=0; j < Vertices.length; j++)
				if( Labels[2*i].equals(Vertices[j].getLabel()) )
					for(int k=0; k < Vertices.length; k++)
						if( Labels[2*i+1].equals(Vertices[k].getLabel()) )
							if(this instanceof UndirectedGraph)
								newAdjacencies[i+Adjacencies.length] = new Edge(Vertices[j],Vertices[k]);
							else if(this instanceof DirectedGraph)
								newAdjacencies[i+Adjacencies.length] = new Arc(Vertices[j],Vertices[k]);
		if(this instanceof UndirectedGraph)
			this.setAdjacencies((Edge[]) newAdjacencies);
		else if(this instanceof DirectedGraph)
			this.setAdjacencies((Arc[]) newAdjacencies);
		this.setSize();
		this.setAdjacenciesIndexes();
		this.buildAdjacencyMatrix();
		this.buildIncidenceMatrix();
		this.setVerticesNeighbourhoods();
	}
	
	public void add_Elements(String[] LABELS, String RELATED_VERTICES_LABELS){
		Vertex[] newVertices = Util.expand(Vertices,LABELS.length);
		if(this instanceof UndirectedGraph){
			for(int i=0; i < LABELS.length; i++)
				newVertices[i+Vertices.length] = new UndirectedGraphVertex(LABELS[i]);
			this.setVertices((UndirectedGraphVertex[]) newVertices);
		} else if(this instanceof DirectedGraph){
			for(int i=0; i < LABELS.length; i++)
				newVertices[i+Vertices.length] = new DirectedGraphVertex(LABELS[i]);
			this.setVertices((DirectedGraphVertex[]) newVertices);
		}
		this.setOrder();
		this.setVerticesIndexes();
		String[] Labels = RELATED_VERTICES_LABELS.split("[ -]+");
		Adjacency[] newAdjacencies = Util.expand(Adjacencies,Labels.length/2);
		if( this instanceof UndirectedGraph ){
			for(int i=0; i < Labels.length/2; i++)
				for(int j=0; j < Vertices.length; j++)
					if( Labels[2*i].equals(Vertices[j].getLabel()) ){
						for(int k=0; k < Vertices.length; k++)
						    if( Labels[2*i+1].equals(Vertices[k].getLabel()) ){
							    newAdjacencies[i+Adjacencies.length] = new Edge(Vertices[j],Vertices[k]);
						    }
					}
			this.setAdjacencies((Edge[]) newAdjacencies);
			
		} else if(this instanceof DirectedGraph){
			for(int i=0; i < Labels.length/2; i++)
				for(int j=0; j < Vertices.length; j++)
					if( Labels[2*i].equals(Vertices[j].getLabel()) )
						for(int k=0; k < Vertices.length; k++)
						    if( Labels[2*i+1].equals(Vertices[k].getLabel()) )
							    newAdjacencies[i+Adjacencies.length] = new Arc(Vertices[j],Vertices[k]);
			this.setAdjacencies((Arc[]) newAdjacencies);
		}
		this.setSize();
		this.setAdjacenciesIndexes();
		this.buildAdjacencyMatrix();
		this.buildIncidenceMatrix();
		this.setVerticesNeighbourhoods();
	}
	
	public void remove_Vertices(String[] LABELS){
		Vertex[] newVertices;
		if(this instanceof UndirectedGraph)
			newVertices = new UndirectedGraphVertex[Vertices.length - LABELS.length];
		else
			newVertices = new DirectedGraphVertex[Vertices.length - LABELS.length];
		int n = 0;
		for(int i=0; i < Vertices.length; i++){
			boolean ok = true;
			for(int j=0; j < LABELS.length; j++)
				if( Vertices[i].getLabel().equals( LABELS[j] ) )
					ok = false;
			if(ok)
				if(this instanceof UndirectedGraph)
					newVertices[n++] = new UndirectedGraphVertex(Vertices[i].getLabel());
				else
					newVertices[n++] = new DirectedGraphVertex(Vertices[i].getLabel());
		}
		this.setVertices(newVertices);
		this.setOrder();
		this.setVerticesIndexes();
		
		Adjacency[] newAdjacencies;
		if(this instanceof UndirectedGraph)
			newAdjacencies = new Edge[Adjacencies.length];
		else
			newAdjacencies = new Arc[Adjacencies.length];
		int m = 0;
		for(int i=0; i < Adjacencies.length; i++)
			    if( this.contains( Adjacencies[i].getIncidentVertices()[0] ) && 
					    this.contains( Adjacencies[i].getIncidentVertices()[1] ) )
			    	if(this instanceof UndirectedGraph)
			    		newAdjacencies[m++] = new Edge(Vertices[ this.getVertexIndex( Adjacencies[i].getIncidentVertices()[0].getLabel() ) ],
						    Vertices[ this.getVertexIndex( Adjacencies[i].getIncidentVertices()[1].getLabel() ) ] );
			    	else
			    		newAdjacencies[m++] = new Arc(Vertices[ this.getVertexIndex( Adjacencies[i].getIncidentVertices()[0].getLabel() ) ],
						    Vertices[ this.getVertexIndex( Adjacencies[i].getIncidentVertices()[1].getLabel() ) ] );
		this.setAdjacencies(java.util.Arrays.copyOfRange(newAdjacencies,0,m));
		this.setSize();
		this.setAdjacenciesIndexes();
		this.buildAdjacencyMatrix();
		this.buildIncidenceMatrix();
		this.setVerticesNeighbourhoods();
	}
	
	public void remove_Adjacencies(String RELATED_VERTICES_LABELS){
		String[] Labels = RELATED_VERTICES_LABELS.split("[ -]+");
		boolean[][] Matrix = AdjacencyMatrix;
		if(this instanceof UndirectedGraph)
			for(int i=0; i < Labels.length/2; i++){
				Matrix[this.getVertexIndex(Labels[2*i])][this.getVertexIndex(Labels[2*i+1])] = false;
			    Matrix[this.getVertexIndex(Labels[2*i+1])][this.getVertexIndex(Labels[2*i])] = false;
			}
		else if(this instanceof DirectedGraph)
			     for(int i=0; i < Labels.length/2; i++)
				    Matrix[this.getVertexIndex(Labels[2*i])][this.getVertexIndex(Labels[2*i+1])] = false;
		this.setAdjacencies(Matrix);
		this.setSize();
		this.setAdjacenciesIndexes();
		this.buildAdjacencyMatrix();
		this.buildIncidenceMatrix();
		this.setVerticesNeighbourhoods();
	}
	
	public abstract void remove_Elements(String[] LABELS, String RELATED_VERTICES_LABELS);
	
	public int getVertexIndex(String label){
		for(int i=0; i < Vertices.length; i++)
			if( Vertices[i].getLabel().equals( label ) )
				return i;
		return -1;
	}
}