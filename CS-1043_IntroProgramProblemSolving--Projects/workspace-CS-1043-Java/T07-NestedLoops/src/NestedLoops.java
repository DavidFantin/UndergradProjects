/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/1/18
Assignment: Write a nested loop to generate a grid of stars in different patterns
Description: 
*/

import java.util.Scanner;

public class NestedLoops 
{	public static void main(String[] args) 
	{	System.out.println("Enter number of output lines: ");
			Scanner console = new Scanner(System.in);
			int nR = console.nextInt();
			System.out.println("Enter the length of each lines: ");
			int nC = console.nextInt();
			// nested loop goes here:

			for (int ir = 1; ir <= nR; ir++)	// Outer loop
			{
					for (int ic = 1; ic <= nC; ic++)	// Inner loop
						{	System.out.print('*');	}
					
			System.out.println();
			}
			
//Problem 2

		System.out.println("Enter number of output lines: ");
		nR = console.nextInt();
		// nested loop goes here:
		System.out.println("Problem 2 Pattern");
		for (int ir=1; ir <= 4; ir++)
			{
			for ( int ic=1; ic <= ir; ic++ )
				{	System.out.print('*'); }
			System.out.println();
			}	
		
//Problem 3
		
		System.out.println("Enter number of output lines: ");
		nR = console.nextInt();
		// nested loop goes here:
		System.out.println("Problem 3 Pattern");
		for ( int ir=nR; ir >= 1; ir-- )
			{
			for ( int ic=1; ic <=ir; ic++ )
				{	System.out.print('*'); }
			System.out.println();
			}

//Problem 4
		
		System.out.println("Enter number of output lines: ");
		nR = console.nextInt();
		// nested loop goes here:
		System.out.println("Problem 4 Pattern");
		for ( int ir=1; ir <= nR; ir++ )
			{
			int nB = nR-ir;
			for ( int ic=1; ic <=nB; ic++ )
			{	System.out.print(' '); }
			for ( int ic=1; ic <=ir; ic++ )
				{	System.out.print('*'); }
			System.out.println();
			}
		
//Problem 5
		
		System.out.println("Enter number of output lines: ");
		nR = console.nextInt();
		// nested loop goes here:
		System.out.println("Problem 5 Pattern");
		for ( int ir=1; ir <= 4; ir++ )
			{
			int nB =  4;
			for ( int ic=1; ic <=nB; ic++ )
			{	System.out.print(' '); }
			for ( int ic=1; ic <=ir; ic++ )
				{	System.out.print('*'); }
			System.out.println();
			}
		
//Problem 6 EXTRA
		
		System.out.println("Problem 6 Pattern");
		nR = 5;
		int postBlanks = 7;
		
		console.close();
	}// end main
}// end class