/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/01/18
Assignment: Lab 11
Description: Read and write text files
*/

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

public class LabIO {
	private double[][] data;
	//rows = data.length; 
	//cols = data[i].length;
	public static void main( String [] args )
		throws IOException { LabIO fileTest = new LabIO(); fileTest.readData();
		System.out.println( fileTest.toString() );
		fileTest.writeData(); }

	public void readData(){
		boolean tryAgain;
		Scanner tokens = null;

		do 
		{
			Scanner console = new Scanner(System.in);
			String outMsg = "Enter the input data filename: ";
			String inFile = JOptionPane.showInputDialog(outMsg);
			tryAgain = false;
		
			try {
				File file = new File(inFile);
				tokens = new Scanner(file);
				
				int nrows = tokens.nextInt();
				int ncols = tokens.nextInt();
				
				data = new double[nrows][ncols];
				for (int ir = 0; ir < nrows; ir++) {
					for (int jc = 0; jc < ncols; jc++) {
						double value = tokens.nextDouble();
						data[ir][jc] =  value;
					}
				}
				tokens.close();

			} catch (FileNotFoundException er) {
				System.out.println("Error opening file, enter a valid filename");
				tryAgain = true;
			}
		} while (tryAgain);

	} // end readData()
	
	public String toString() {
		String readData = "";
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				readData += String.format("%11.4f", data[i][j]);
			}
			readData += String.format("\n");
		}
		return readData;
	}

	public void writeData(){       
		
		final int NROWS = 6;
		final int NCOLS = 4;
		final String NAME = "David J. Fantin";
		
		Scanner console = new Scanner(System.in);
		String outMsg2 = "Enter the input data filename: ";
		String inFile = JOptionPane.showInputDialog(outMsg2);
		
		try {
			FileWriter writeIt = new FileWriter(inFile);
			PrintWriter out = new PrintWriter(writeIt);

			out.printf("%d\n", NROWS);
			out.printf("%d\n", NCOLS);

			for (int ir = 0; ir < NROWS; ++ir) {
				for (int jc = 0; jc < NCOLS; ++jc) {
					double value = data[ir][jc];
					out.printf("%11.4f", value); // Right justify
				}
				out.printf("\n");
			}
			out.printf("%s\n", NAME);
			out.close(); 
		} catch (IOException er) {
			System.out.printf("IOException error %s\n", er);
		}
		System.out.printf("Program Terminated OK");
	}
 
}