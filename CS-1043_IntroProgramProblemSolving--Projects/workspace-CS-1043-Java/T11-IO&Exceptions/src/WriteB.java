
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/29/18
Assignment: Write a program to create a .txt document
Description: This code creates a .txt document
*/

import java.io.*;
import java.util.Scanner;

public class WriteB {
	public static void main(String[] args) {
		final int NROWS = 8;
		final int NCOLS = 5;

		Scanner console = new Scanner(System.in);
		System.out.printf("Enter a file name for writing data: ");
		String outputfile = console.next();

		try {
			FileWriter writeIt = new FileWriter(outputfile);
			PrintWriter out = new PrintWriter(writeIt);

			out.printf("%d\n", NROWS);
			out.printf("%d\n", NCOLS);

			for (int ir = 0; ir < NROWS; ++ir) {
				double value = 100.0;
				for (int jc = 0; jc < NCOLS; ++jc) {
					value = Math.pow(value, (1.0 - (ir + jc) / 10.0));
					out.printf("%12.4f", value); // Right justify
				}
				out.printf("\n");
			}
			out.close(); // close the file to empty the IO buffers.
		} catch (IOException er) {
			System.out.printf("IOException error %s\n", er);
		}
		System.out.printf("Program Terminated OK");
	}
}
