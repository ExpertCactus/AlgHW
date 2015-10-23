package AlgHW;


/*
 * Title: TS.java
 * Abstract:
 * 
 * Name: Markus Shaw
 * ID: 0893
 * Date: 
 */
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
public class TS {

	public static void main(String[] args) {
		int numNodes;
		ArrayList<Integer> startNodes = null, endNodes = null;
		String filename;
		ArrayList<String> lines = new ArrayList<String>();
		int[][] graph;
		boolean isDag = true;//will set to false once it is determined if the graph is a dag
		Scanner keyboard = new Scanner(System.in);
		Scanner fileReader = null;
		//System.out.println(System.getProperty("user.dir"));
		System.out.print("Input file name: ");
		
		filename = keyboard.nextLine();
		
		FileReader freader;
		BufferedReader buffReader = null;
		try {
			fileReader = new Scanner(new File(filename));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		numNodes = fileReader.nextInt();
			
		System.out.println("num nodes: " + numNodes);//returns 51 when it should be 3
		graph = new int[numNodes][numNodes];
		
		
		int num = 0;
		String line = "";
		fileReader.nextLine();
		fileReader.useDelimiter(",");
		while(fileReader.hasNextLine())
		{
			line = fileReader.nextLine();
			line = line.replace(",", "");
			lines.add(line);
			//System.out.println(line);
		}
		//System.out.println("lines: " + lines);
		
		for(int k = 0; k < numNodes;k++)
		{
			for(int j = 0; j < numNodes;j++)
			{
				graph[k][j] = Character.getNumericValue(lines.get(k).charAt(j));
			}
		}
		System.out.println("Our node graph:");
		for(int k = 0; k < numNodes;k++)
		{
			for(int j = 0; j < numNodes;j++)
			{
				System.out.print(graph[k][j]);
			}
			System.out.println();
		}
		
		//check for DAG
		//find start node (column will be all 0) can be multiple
		//find end node (row will be all 0) don't know if there are multiple
		//if both of these exist, then it is a dag, if one of them cannot
		//be found, then it is not a dag
		//isDag = checkForDag(graph);
		int startNodeIndex = findStartNode(graph, startNodes);
		System.out.println("Start Node Index: " + startNodeIndex);
		fileReader.close();
		keyboard.close();

	}
	//works for 1, return vector of start nodes, instead of returning, 
	//add start node to vector, and keep going.
	//if no start node is found, add -1 to the vector and return that.
	//can then check the vector or arraylist at position 0 if it is -1,
	//if it is, end the program, we don't have a DAG
	public static int findStartNode(int[][]graph, ArrayList<Integer> startNodes){
		//returns -1 if not found
		//find the lowest index value start node, there could be multiple start nodes
		//start with finding 1 start node
		int sum = 0;
		int row = 0, col=0;
		for(col=0;col<graph.length;col++)//usually rows, in this case is columns
		{
			while(row != graph.length-1)
			{
				sum+=graph[row][col];
				row++;
			}
			
			if(sum == 0)
				return col;
			else
			{
				sum = 0;
				row = 0;
			}
				
			
//			for(row=0;row<graph.length;row++)//usually columns in this case is rows.
//			{
//				sum+= graph[row][col];
//				if(col == graph.length-1)
//				{
//					if(sum == 0)
//						return row;
//					else
//						sum = 0;
//				}
//				
//			}//end for
			
			
		}
		return -1;
	}
	
	//public static int findEndNode(int[][]graph){
		//returns -1 if not found
	//}
	
	public static boolean checkForDag(int[][] graph)
	{
		for(int k = 0;k < graph.length;k++)
		{
			for(int j = 0; j < graph.length;j++)
			{
				if(graph[k][j] == 1 && graph[j][k] == 1)//need to understand conditions
					return false;
				
			}
		}
		//System.out.println("graph length: " + graph.length);
		return true;
	}

}
