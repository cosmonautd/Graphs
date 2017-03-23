package graph_util;

import graph_elements.*;
import graph_types.*;
import java.util.*;
import java.io.*;

public abstract class Util {
	
	public static Graph randomGraph(int order, boolean directed){
		
		Random random = new Random();
		
		if(!directed){
			
			UndirectedGraphVertex[] Vertices = new UndirectedGraphVertex[order];
			for(int i=0; i < order; i++)
				Vertices[i] = new UndirectedGraphVertex(String.format("%d",i+1));
			
			boolean[][] adjMatrix = new boolean[order][order];
			
			int size = random.nextInt((order*(order-1)/2)+1);
			
			for(int i=0; i < size; i++){
				
				int n = random.nextInt(order);
				int m = random.nextInt(order);
				
				adjMatrix[n][m] = true;
				adjMatrix[m][n] = adjMatrix[n][m];
			}
			
			return new UndirectedGraph(Vertices,adjMatrix);
			
		} else {
			
			DirectedGraphVertex[] Vertices = new DirectedGraphVertex[order];
			for(int i=0; i < order; i++)
				Vertices[i] = new DirectedGraphVertex(String.format("%d",i));
			
			boolean[][] adjMatrix = new boolean[order][order];
			
			int size = random.nextInt((order*(order-1))+1);
			
			for(int i=0; i < size; i++){
				
				int n = random.nextInt(order);
				int m = random.nextInt(order);
				
				adjMatrix[n][m] = true;
			}
			
			return new DirectedGraph(Vertices,adjMatrix);
			
		}
	}
	
	public static Vertex[] VerticesDifference(Graph X, Graph Y){
		
		int d = 0;
		Vertex[] Difference = new Vertex[ X.getOrder() ];
		
		for( Vertex V: X.getVertices() )
			if( ! Y.contains(V) )
				Difference[d++] = V;
		
		return java.util.Arrays.copyOfRange(Difference,0,d);
	}
	
	public static Adjacency[] AdjacenciesDifference(Graph X, Graph Y){
		
		int d = 0;
		Adjacency[] Difference = new Adjacency[ X.getSize() ];
		
		for( Adjacency A: X.getAdjacencies() )
			if( ! Y.contains(A) )
				Difference[d++] = A;
		
		return java.util.Arrays.copyOfRange(Difference,0,d);
	}
	
	public static Vertex[] readVertices( String s, String type ){
		
		if( type == "undirected"){
			
			String[] S = s.split("[ ]+");
			UndirectedGraphVertex[] V = new UndirectedGraphVertex[S.length];
			
			for(int i=0; i < V.length; i++){
				V[i] = new UndirectedGraphVertex(S[i]);
				
			}
			return V;
		}
		else if( type == "directed" ){
			
			String[] S = s.split("[ ]+");
			DirectedGraphVertex[] V = new DirectedGraphVertex[S.length];
			
			for(int i=0; i < V.length; i++)
				V[i] = new DirectedGraphVertex(S[i]);
			return V;
		}
		
		return null;
	}
	
	public static Adjacency[] readAdjacencies( String s, Vertex[] V ){
		
		int n = 2;
		boolean labels = false, weights = false;
		
		if(s.contains("&")){
			n++;
			labels = true;
		}
		
		if(s.contains("#")){
			n++;
			weights = true;
		}
		
		String[] S = s.split("[ #&-]");
		
		Adjacency A[];
		
		if( V instanceof UndirectedGraphVertex[] )
			A = new Edge[S.length/n];
		else
			if( V instanceof DirectedGraphVertex[] )
				A = new Arc[S.length/n];
			else
				return null;
		
		for(int i=0; i < A.length; i++)
			for(int j=0; j < V.length; j++)
				if( S[n*i].equals(V[j].getLabel()) )
					for(int k=0; k < V.length; k++)
						if( S[n*i+1].equals(V[k].getLabel()) )
							if(labels && weights)
								if( V instanceof UndirectedGraphVertex[] )
								    A[i] = new Edge(V[j],V[k],Double.parseDouble(S[n*i+3]),S[n*i+2]);
								else A[i] = new Arc(V[j],V[k],Double.parseDouble(S[n*i+3]),S[n*i+2]);
							else 
								if(labels)
									if( V instanceof UndirectedGraphVertex[] )
									    A[i] = new Edge(V[j],V[k],S[n*i+2]);
									else A[i] = new Arc(V[j],V[k],S[n*i+2]);
								else
									if(weights)
										if( V instanceof UndirectedGraphVertex[] )
										    A[i] = new Edge(V[j],V[k],Double.parseDouble(S[n*i+2]));
										else A[i] = new Arc(V[j],V[k],Double.parseDouble(S[n*i+2]));
									else
										if( V instanceof UndirectedGraphVertex[] )
										    A[i] = new Edge(V[j],V[k]);
										else A[i] = new Arc(V[j],V[k]);
			return A;
	}
	
	public static byte[][] getSubMatrix( byte[][] Matrix, int[] rows, int[] columns ){
		
		byte[][] subMatrix = new byte[rows.length][columns.length];
		int x = 0, y = 0;
		
		for(int i: rows){
			for(int j: columns){
				subMatrix[x][y++] = Matrix[i][j];
			}
			x++;
			y = 0;
		}
		
		return subMatrix;
	}
	
	public static boolean[][] getSubMatrix( boolean[][] Matrix, int[] rows, int[] columns ){
		
		boolean[][] subMatrix = new boolean[rows.length][columns.length];
		int x = 0, y = 0;
		
		for(int i: rows){
			for(int j: columns){
				subMatrix[x][y++] = Matrix[i][j];
			}
			x++;
			y = 0;
		}
		
		return subMatrix;
	}
	
	public static Vertex[] expand(Vertex[] X, int increment){
		
		Vertex[] Y;
		
		if( X instanceof UndirectedGraphVertex[] ){
			Y = new UndirectedGraphVertex[X.length + increment];
			System.arraycopy(X,0,Y,0,X.length);
			return Y;
		}
		else if( X instanceof DirectedGraphVertex[] ){
			Y = new UndirectedGraphVertex[X.length + increment];
			System.arraycopy(X,0,Y,0,X.length);
			return Y;
		}
		
		return null;
	}
	
	public static Adjacency[] expand(Adjacency[] X, int increment){
		
		Adjacency[] Y;
		
		if( X instanceof Edge[] ){
			Y = new Edge[X.length + increment];
			System.arraycopy(X,0,Y,0,X.length);
			return Y;
		}
		else if( X instanceof Arc[] ){
			Y = new Arc[X.length + increment];
			System.arraycopy(X,0,Y,0,X.length);
			return Y;
		}
		
		return null;
	}
	
	public static String readFile(String directory){
		
		String path = "", file;
		String[] S = directory.split("/");
		for(int i=0; i < S.length - 1; i++)
			path+=(S[i]+"/");
		file = S[S.length-1];
		
		File dir = new File(path);
		File arq = new File(dir, file);
		String text = "";
		
		try {
			
			FileReader reader = new FileReader(arq);
			BufferedReader reading = new BufferedReader(reader);
			String line = "";
			while( ( line = reading.readLine() ) != null ) text+=line; 
			reader.close();
			reading.close();
			
		} catch( IOException e) { 
			
			e.printStackTrace(); 
			
		}
		
		return text;
	}
	
	public static void writeFile(String text, String directory){
		
		try {
			
			FileWriter outFile = new FileWriter(directory,true);
			PrintWriter out = new PrintWriter(outFile);
			
			out.print(text);
			out.close();
			
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void clearFile(String directory){
		
		try {
			
			FileWriter outFile = new FileWriter(directory);
			PrintWriter out = new PrintWriter(outFile);
			
			out.print("");
			out.close();
			
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}
}