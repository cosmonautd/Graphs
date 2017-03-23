import graph_elements.*;
import graph_types.*;
import graph_util.*;
import java.util.Scanner;

public class Graph_Test {
	
	public static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.print("Type the vertices (e.g. 1 2 3 4 5): ");
		UndirectedGraphVertex[] V = (UndirectedGraphVertex[]) Util.readVertices( input.nextLine(), "undirected" );
		
		System.out.print("Type the adjacencies (e.g. 1-2 2-4 3-4 3-5): ");
		Edge[] A = (Edge[]) Util.readAdjacencies( input.nextLine(), V );
		
		Graph X = new UndirectedGraph(V,A);

        X.printInfo();

        System.out.println();

        boolean[][] transitiveClosure = Paths.transitiveClosureMatrix(X);

        System.out.println("Transitive Closure Matrix:\n");
        for(int i=0; i < X.getOrder(); i++){
            for(int j=0; j < X.getOrder(); j++)
                if( transitiveClosure[i][j] )
                    System.out.print("1 ");
                else System.out.print("0 ");
            System.out.println();
        }

		String[] S;

		do {

			System.out.print("\n\nType the vertices you want to search for connection (e.g. 1 5): ");
			S = input.nextLine().split("[ ]");

			if(!S[0].equals("exit")) {
                System.out.print("\nShortest Path: " + Paths.shortestPath(X, S[0], S[1]));
                System.out.print("\nShortest Distance: " + Paths.shortestPathLength(X, S[0], S[1]));
            }
		
		} while(!S[0].equals("exit"));
	}
}