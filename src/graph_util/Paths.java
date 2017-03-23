package graph_util;

//import graph_elements.*;
import graph_types.*;

public abstract class Paths {
	
	private static int counter = 0;
	private static boolean[] flags;
	
	private static void set_flags(Graph G){
		flags = new boolean[G.getVertices().length];
	}
	
	public static boolean check_connection(Graph G, String start, String end){
		
		counter++;
		
		if( counter == 1 ) set_flags(G);
		
		try {
		
		    if( start.equals(end) )
			    return true;
		    
		    int start_index = G.getVertexIndex(start), end_index = G.getVertexIndex(end);
		    boolean[][] adjMatrix = G.getAdjacencyMatrix();
		    
		    if( adjMatrix[start_index][end_index] )
		    	return true; // TESTAR!
		    
		    for(int i=0; i < flags.length; i++)
			    if( adjMatrix[start_index][i] && !flags[i] ){
				    flags[i] = true;
				    if( i == end_index )
					    return true;
				    else if (check_connection(G,G.getVertices()[i].getLabel(),end))
					    return true;
			    }
		
		    return false;
		
		} finally { 
			
			counter--;
			if( counter == 0 ) set_flags(G);
		}
	}
	
	public static String path(Graph G, String start, String end){
		
		counter++;
		
		if( counter == 1 ) set_flags(G);
		
		try {
		
		    if( start.equals(end) )
			    return end;
		
		    int start_index = G.getVertexIndex(start), end_index = G.getVertexIndex(end);
		    boolean[][] adjMatrix = G.getAdjacencyMatrix();
		    
		    if(adjMatrix[start_index][end_index])
		    		if(counter > 1)
		    			return "!:@:#:"+ end;
		    		else
		    			return start + " " + end;
		    
		    flags[start_index] = true;
		    
		    for(int i=0; i < flags.length; i++)
			    if( adjMatrix[start_index][i] && !flags[i] ){
				    flags[i] = true;
				    if( i == end_index )
				    	if( counter > 1 )
				    		return "!:@:#:"+ end;
				    	else 
				    		return start + " " + end;
				    else {
				    	
				    	String aux = path(G,G.getVertices()[i].getLabel(),end);
				    	if ( aux.substring(0,6).equals("!:@:#:") )
				    		if( counter > 1 )
				    			return String.format("!:@:#:%s", G.getVertices()[i].getLabel()) + " " + aux;
				    		else
				    			return start + String.format(" %s", G.getVertices()[i].getLabel()) + " " + aux.replaceAll("!:@:#:","");
				    }
			    }
		
		    return end + " is unreachable from " + start;
		
		} finally { 
			
			counter--;
			if( counter == 0 ) set_flags(G);
		}
	}
	
	public static double shortestPathLength(Graph G, String start, String end){ // Using Dijkstra's Algorithm
		
		set_flags(G);
		
		double[] distances = new double[flags.length];
		for(int i=0; i < distances.length; i++)
			distances[i] = Double.POSITIVE_INFINITY;
		
		int current_index = G.getVertexIndex(start), end_index = G.getVertexIndex(end);
		boolean[][] adjMatrix = G.getAdjacencyMatrix();
		byte[][] incMatrix = G.getIncidenceMatrix();
		
		distances[current_index] = 0;
		
		try {
			
			while( !flags[end_index] ){
			
			for(int i=0; i < flags.length; i++)
				if( adjMatrix[current_index][i] )
					for(int j=0; j < incMatrix[0].length; j++)
						if( incMatrix[current_index][j] != 0 && incMatrix[i][j] == 1 )
							if( distances[current_index] + G.getAdjacencies()[j].getWeight() < distances[i] )
								distances[i] = distances[current_index] + G.getAdjacencies()[j].getWeight();
			
			flags[current_index] = true;
			
			current_index = nextCurrentIndex(distances);
			
			}
		
		} catch( Exception error ) {
			
			return Double.POSITIVE_INFINITY;
		}
		
		return distances[end_index];
	}
	
	private static int nextCurrentIndex(double[] doubleArray){
		
		double minValue = Double.POSITIVE_INFINITY;
		int nextCurrentIndex = -1;
		
		for(int i=0; i < doubleArray.length; i++)
			if( doubleArray[i] < minValue && !flags[i]){
				minValue = doubleArray[i];
				nextCurrentIndex = i;
			}
		
		return nextCurrentIndex;
	}
	
	public static String shortestPath(Graph G, String start, String end){ // Using Dijkstra's Algorithm
		
		set_flags(G);
		
		double[] distances = new double[flags.length];
		for(int i=0; i < distances.length; i++)
			distances[i] = Double.POSITIVE_INFINITY;
		
		int current_index = G.getVertexIndex(start), end_index = G.getVertexIndex(end);
		String[] shortestPaths = new String[distances.length];
		boolean[][] adjMatrix = G.getAdjacencyMatrix();
		byte[][] incMatrix = G.getIncidenceMatrix();
		
		distances[current_index] = 0;
		shortestPaths[current_index] = start;
		
		try {
			
			while( !flags[end_index] ){
			
			for(int i=0; i < flags.length; i++)
				if( adjMatrix[current_index][i] )
					for(int j=0; j < incMatrix[0].length; j++)
						if( incMatrix[current_index][j] != 0 && incMatrix[i][j] == 1 )
							if( distances[current_index] + G.getAdjacencies()[j].getWeight() < distances[i] ){
								distances[i] = distances[current_index] + G.getAdjacencies()[j].getWeight();
								shortestPaths[i] = shortestPaths[current_index] + " " + G.getVertices()[i].getLabel();
							}
			
			flags[current_index] = true;
			
			current_index = nextCurrentIndex(distances);
			
			}
			
		} catch(Exception error){
			
			return end + " is unreachable from " + start; 
		}
		
		return shortestPaths[end_index];
	}
	
	public static boolean[][] transitiveClosureMatrix(Graph G){ // Warshall's algorithm
		
		boolean[][] transitiveClosureMatrix = new boolean[G.getOrder()][G.getOrder()];
		
		for(int i=0; i < G.getOrder(); i++)
			for(int j=0; j < G.getOrder(); j++)
				transitiveClosureMatrix[i][j] = G.getAdjacencyMatrix()[i][j];
		
		for(int i=0; i < G.getOrder(); i++)
			for(int j=0; j < G.getOrder(); j++)
				if( transitiveClosureMatrix[j][i] )
					for(int k=0; k < G.getOrder(); k++)
						transitiveClosureMatrix[j][k] = transitiveClosureMatrix[j][k] || transitiveClosureMatrix[i][k];
		
		return transitiveClosureMatrix;
	}
}