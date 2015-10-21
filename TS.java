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
public class TS {

	public static void main(String[] args) {
		int numNodes;
		String filename;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Input file name: ");
		filename = keyboard.nextLine();
		FileReader freader;
		BufferedReader buffReader = null;
		try {
			 freader = new FileReader(filename);
			 buffReader = new BufferedReader(freader);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			numNodes = buffReader.read();
			System.out.println("num nodes: " + numNodes);//returns 51 when it should be 3
			buffReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
