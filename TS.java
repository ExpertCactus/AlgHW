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
import java.util.Stack;
public class TS {

	public static void main(String[] args) {
		int numNodes;
		Stack<Integer> order = new Stack<Integer>();
		boolean[] visited;
		ArrayList<Integer> startNodes = null, endNodes = null,topSort = null;
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
		visited = new boolean[numNodes];
		
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
		startNodes = findStartNode(graph, startNodes);
		endNodes = findEndNodes(graph,endNodes);
		System.out.println("Start Node Indexes: " + startNodes);
		System.out.println("End Node Indexes: " + endNodes);
		
		
		//decide if this is a dag
		if(startNodes.get(0) == -1 || endNodes.get(0) == -1)
		{
			System.out.println("This is not a DAG");
			System.exit(1);
		}
		
		System.out.println("This is a DAG");
		System.out.print("Start Node(s): ");
		for(int k =0; k < startNodes.size();k++)
		{
			if(k == startNodes.size()-1)
				System.out.println(startNodes.get(k) +1);
			else
				System.out.print(startNodes.get(k) +1 + ", ");
			
		}
		
		//matrix traversal
		//send to graph traversal method, send stack along with. and arrayList of topological order
		
		//order= traverseGraph(start,visited,topSort,graph,startNodes,endNodes);
		System.out.println("graph length : " + graph.length);
		for(int j = 0;j<visited.length;j++)
			visited[j] = false;
		topSort = new ArrayList<Integer>();
		int temp = 0;
		for(int k = 0; k < startNodes.size();k++)
		{
			temp = DFS(startNodes.get(k),visited,order,topSort,graph,endNodes);
			topSort.add(temp);
		}
		
		System.out.print("Visited: ");
		for(int c = 0;c< visited.length;c++)
			System.out.print(visited[c] + " ");
		System.out.println();
		
		System.out.println("order: " + order);
		System.out.println("topSort: " + topSort);
		
		fileReader.close();
		keyboard.close();

	}
	
	//if we have not visited the start node already, 
	//push it to the stack
	//if we have, move to the next start node. in a while loop, 
	//while not visited
//	public static int DFS(int start, boolean[]visited,Stack<Integer> 
//	  order,ArrayList<Integer>topSort, int[][]graph,ArrayList<Integer>endNodes)
//	{
//		DFSAux(start,visited,order,topSort,graph,endNodes);
//		return topSort;
//	}
	
	public static int DFS(int start, boolean[]visited,Stack<Integer> 
				  order,ArrayList<Integer>topSort, int[][]graph,ArrayList<Integer>endNodes)
	{
		int row = start, col = 0;
		visited[row] = true;
		order.push(row);
		
		if(row == endNodes.get(0))
		{
			//topSort.add(new Integer(row));
			return order.pop() + 1;//row + 1;
		}
		
		for(col = 0; col < graph.length;col++)
		{
			
			
			if(graph[row][col] == 1)
			{
				if(!visited[col])
				{
					//if(!order.contains(new Integer(col)))
						//order.push(new Integer(col));
					//order.push(col);
					Integer temp = new Integer(DFS(col,visited,order,topSort,graph,endNodes));
					if(!topSort.contains(temp));
						topSort.add(temp);
					
				}else if(col == graph.length-1)
					return order.pop() + 1;
				
			}
		}
		return order.pop() + 1;
		//return topSort;
	}
	//works for 1, return vector of start nodes, instead of returning, 
	//add start node to vector, and keep going.
	//if no start node is found, add -1 to the vector and return that.
	//can then check the vector or arraylist at position 0 if it is -1,
	//if it is, end the program, we don't have a DAG
	public static ArrayList<Integer> findStartNode(int[][]graph, ArrayList<Integer> startNodes){
		//returns -1 if not found
		//find the lowest index value start node, there could be multiple start nodes
		//start with finding 1 start node
		int sum = 0;
		int row = 0, col=0;
		startNodes = new ArrayList<Integer>();
		startNodes.add(new Integer(-1));//inital value of -1, means no start nodes
		boolean done = false;
		//for(row=0;row<graph.length;row++)//usually rows, in this case is columns
		//{//start for
		while(!done)
		{
			
			
			if(graph[row][col] != 0 )//row should be the only value changing
			{
				col++;//if row,col is not 0, then move to the next column
			}else if(row == graph.length-1)//if row is at the bottom of the column,
			{
				if(startNodes.get(0) == -1)//if this is the first time finding a start node
					startNodes.clear();
				startNodes.add(new Integer(col));//then add that column to the start node list
				col++;
			}else if(row > graph.length-1 || col > graph.length-1)
			{
				done = true;
			}
			
			if(col > graph.length -1)
			{
				done = true;
			}else if(row == graph.length-1)
			{
				//if(col < graph.length -1)
					//col++;
				row = 0;
			}else				
				row++;
		}
			
			
			
		//}//end for
		return startNodes;//return the list of start nodes, hopefully with no -1.
	}
	
	public static ArrayList<Integer> findEndNodes(int[][] graph, ArrayList<Integer> endNodes)
	{
		//col should be the only index that moves continuously.
		//row changes only if we find a nonzero integer in the row
		//an end node will have all 0s
		boolean done = false;
		endNodes = new ArrayList<Integer>();
		endNodes.add(new Integer(-1));
		int row = 0, col=0;
		
		while(!done)
		{
			
			if(graph[row][col] != 0 )
			{
				row++;
				col = 0;
			}else if(col == graph.length-1)
			{
				if(endNodes.get(0) == -1)//if this is the first time finding a start node
					endNodes.clear();
				endNodes.add(new Integer(row));//then add that column to the start node list
				row++;
			}else if(row > graph.length-1 || col > graph.length-1)
			{
				done = true;
			}
			
			
			if(row > graph.length -1)
			{
				done = true;
			}else if(col == graph.length-1)
			{
				//if(col < graph.length -1)
					//col++;
				col = 0;
			}else				
				col++;
		}
		
		return endNodes;
	}
//			while(row != graph.length-1)
//			{
//				sum+=graph[row][col];
//				row++;
//			}
//			if (row >= graph.length)
//			{
//				startNodes.add(new Integer(-1));
//				return startNodes;
//			}else if(sum == 0)
//			{
//				startNodes.add(new Integer(row));
//				//return col;
//			}else
//			{
//				sum = 0;
//				row = 0;
//			}
				
			
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
