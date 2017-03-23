package graph_apps;

import graph_util.*;
import java.io.*;
import java.util.*;

public class BuildGraph {
	
	static Scanner input = new Scanner(System.in);
	static Scanner input2 = new Scanner(System.in);
	static byte option = 0;
	
	public static void main(String[] args) {
		
		String mainDirectory = System.getProperty("user.dir");
		mainDirectory += "/BuildGraph";

		System.out.println("Using " + mainDirectory);
		
		System.out.print("\nBuild Graph");
		
		if ( ! new File(mainDirectory).exists() )
			new File(mainDirectory).mkdir();
		
		String graphDirectory = mainDirectory;
		
		do {
			
			boolean graphChoiceOK = false;
			
		    do {
		
		        System.out.print("\n\n0. Exit\n1. New Graph\n2. Edit Existing Graph\n3. View Existing Graphs\n");

		        do {
			
			        getOption();
		    
		            if( option < 0 || option > 3 )
		    	        System.out.println("               Invalid option.");
		
		        } while (option < 0 || option > 3);
		
		        switch( option ) {
		        
		            case 0: {
		            	
		            	System.exit(0);
		            }
		        
		            case 1: {
		                
		            	System.out.print("\nGraph name: ");
		                String graphName = input2.nextLine();
		
		                graphDirectory = mainDirectory + "/" + graphName;
		                File graph = new File(graphDirectory);
		
		                if (graph.mkdir()) {
			                System.out.print("\nGraph creation succeded");
			                graphChoiceOK = true;
		                }
		                else if( graph.exists() )
		        	        System.out.print("\nGraph already exists.");
		                     else
		            	         System.out.print("\nGraph creation failed");
		                break;   
		            }
		            
		            case 2: {
		            	
			            System.out.println();
			            
			            File[] list = new File(mainDirectory).listFiles();
			            
			            if( list.length > 0 ){
			            for(int i=0; i < list.length; i++)
			    	        System.out.println(i + ". " + list[i].getName());
			            
			            getOption();
			            
				        graphDirectory = mainDirectory + "/" + list[option].getName();
				        graphChoiceOK = true;
				        
			            } else System.out.print("No existing graphs.\n\n");
			            
			            break;
		            }
		            
		            case 3: {
		            	
		    	        System.out.println();

		    	        File[] list= new File(mainDirectory).listFiles();

		    	        if( list.length > 0 ){
				            for(int i=0; i < list.length; i++)
				    	        System.out.println(i + ". " + list[i].getName());
		    	        }
		    	        else System.out.print("No existing graphs.\n\n");
		    	        
		    	        break;
		            }
		        }
		
		    } while( !graphChoiceOK );
		
		    do {
		
		        System.out.println("\n\n0. Main menu\n1. Add vertex\n2. Add adjacency\n3. Remove vertex\n4. Remove adjacency" +
		        		"\n5. Display vertices\n6. Display adjacencies");
		        getOption();
		
		        switch( option ){
		
		            case 0: {
		        	
		        	    break;
		        	
		            }
		    
		            case 1: {
		    	
		    	        addVertex(graphDirectory);
		    	        break;
		            }
		    
		            case 2: {
		    	
		    	        addAdjacency(graphDirectory);
		    	        break;
		    	
		            }
		            
		            case 3: {
		            	
		            	removeVertex(graphDirectory);
		            	break;
		            }
		            
		            case 4: {
		            	
		            	removeAdjacency(graphDirectory);
		            	break;
		            }
		            
		            case 5: {
		            	
		            	displayVertices(graphDirectory);
		            	break;
		            }
		            
		            case 6: {
		            	
		            	displayAdjacencies(graphDirectory);
		            	break;
		            }
		        }
		
		    } while ( option != 0 );
		
		} while (true);
	}
	
	private static void addVertex(String directory){
		
		System.out.print("\nVertex name: ");
    	String vertexName = input2.nextLine();
    	
    	if((new File(directory+"/Vertices.txt")).exists()){
    		String[] Vertices = Util.readFile(directory+"/Vertices.txt").split(" ");
    		if( Arrays.asList(Vertices).contains(vertexName) )
    			System.out.print("This vertex already exists");
    		else Util.writeFile(vertexName+" ",directory+"/Vertices.txt"); 
    	}
    	else Util.writeFile(vertexName+" ",directory+"/Vertices.txt");
    	
	}
	
	private static void addAdjacency(String directory){
		
		System.out.print("\nStartpoint: ");
    	String startpoint = input2.nextLine();
    	System.out.print("Endpoint: ");
    	String endpoint = input2.nextLine();
    	System.out.print("Adjacency name: ");
    	String adjacencyName = input2.nextLine();
    	System.out.print("Weight: ");
    	String weight = input2.nextLine();
    	Util.writeFile(startpoint+"-"+endpoint+"&"+adjacencyName+"#"+weight+" ",directory+"/Adjacencies.txt");
	}
	
	private static void removeVertex(String directory){
		
		System.out.print("\nVertex name: ");
		String vertex = input2.nextLine();
		
		String resultingVertices = "";
		String[] Vertices = Util.readFile(directory+"/Vertices.txt").split(" ");
		
		for(String v: Vertices)
			if( ! v.equals(vertex) )
				resultingVertices+=(v+" ");
		
		Util.clearFile(directory+"/Vertices.txt");
		Util.writeFile(resultingVertices,directory+"/Vertices.txt");
		
		String resultingAdjacencies = "";
		String[] Adjacencies = Util.readFile(directory+"/Adjacencies.txt").split("[ #&-]");
		
		for(int i=0; i < Adjacencies.length/4; i++)
			if( ! ( Adjacencies[4*i].equals(vertex) || Adjacencies[4*i+1].equals(vertex) ) ){
				resultingAdjacencies+=(Adjacencies[4*i]+"-"+Adjacencies[4*i+1]);
				resultingAdjacencies+=("&" + Adjacencies[4*i+2]);
				resultingAdjacencies+=("#" + Adjacencies[4*i+3] + " ");
			}
		
		Util.clearFile(directory+"/Adjacencies.txt");
		Util.writeFile(resultingAdjacencies,directory+"/Adjacencies.txt");
		
	}
	
	private static void removeAdjacency(String directory){
		
		System.out.print("\nAdjacency name: ");
		String adjacency = input2.nextLine();
		
		String resultingAdjacencies = "";
		String[] Adjacencies = Util.readFile(directory+"/Adjacencies.txt").split("[ #&]");
		
		for(int i=0; i < Adjacencies.length/3; i++)
			if( ! ( Adjacencies[3*i].equals(adjacency) || Adjacencies[3*i+1].equals(adjacency) ) ){
				resultingAdjacencies+=(Adjacencies[3*i]);
				resultingAdjacencies+=("&" + Adjacencies[3*i+1]);
				resultingAdjacencies+=("#" + Adjacencies[3*i+2] + " ");
			}
		
		Util.clearFile(directory+"/Adjacencies.txt");
		Util.writeFile(resultingAdjacencies,directory+"/Adjacencies.txt");
	}
	
	private static void displayVertices(String directory){
		
		System.out.print("\nVertices: " + Util.readFile(directory+"/Vertices.txt") );
	}
	
	private static void displayAdjacencies(String directory){
		
		System.out.print("\nAdjacencies: " + Util.readFile(directory+"/Adjacencies.txt") );
	}
	
	private static void getOption(){
		
		System.out.print("\noption: ");
        option = input.nextByte();
	}
}