/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/29/18
Assignment: Write a program to read a .txt document
Description: This code reads a .txt document
*/

import java.util.Scanner;
import java.io.*;

public class ReadB {
	public static void main(String[] args) throws IOException
	{

		boolean tryAgain;
		Scanner tokens = null;

		do // attempt to open a file.
		{
			Scanner console = new Scanner(System.in);
			System.out.printf("Enter file name to read: ");
			String inputFile = console.next();
			tryAgain = false;

			//try {
				File fileRef = new File(inputFile);// file reference.

				tokens = new Scanner(fileRef);
				int Nrows = tokens.nextInt();// 1st line has one token.
				int Ncols = tokens.nextInt();// 2nd line has one token.

				for (int ir = 0; ir < Nrows; ++ir) {
					for (int jc = 0; jc < Ncols; ++jc) {
						double value = tokens.nextDouble();
						System.out.printf("%10.4f", value);
					}
					System.out.printf("\n");
			}
				/*
			} catch (FileNotFoundException er) {
				System.out.printf("Error opening file %s, %s\n", inputFile, er);
				tryAgain = true;
			} catch (Exception er) {
				System.out.printf("Unrecoverable exception, exiting program %s\n", er);
			} finally {
				if (tokens != null)
					tokens.close();
			}*/
		}
		 while (tryAgain);	

	}	
}