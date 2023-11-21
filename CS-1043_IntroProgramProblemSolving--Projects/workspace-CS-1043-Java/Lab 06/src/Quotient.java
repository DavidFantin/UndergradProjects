/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/27/18
Assignment: Write a program to factor a user supplied integer value.
Description: This program factors
*/

import java.util.Scanner;

public class Quotient {

	public static void main(String[] args) 
	{
		System.out.print("Enter the dividend, a number to factor: " );
		Scanner console = new Scanner( System.in );
		
		int input = console.nextInt();
		int dividend = input;
		int divisor;
		
		System.out.println();
		
		// repeat the following code until the number is factored
		
		while(!(dividend == 1))
		{
			System.out.print("Enter a divisor: " );  // prompt for a divisor
			divisor = console.nextInt();
			// determine if the divisor is a factor of the dividend
			if (dividend % divisor == 0)
			{
				System.out.println( divisor + " is a factor of " + input );
				
				
				dividend /= divisor;  // reduce the dividend
				
				System.out.println( dividend + " is the new dividend.\n");
				
			}
			else
			{
				System.out.println( divisor + " is not a factor, try again for " + dividend );
			}
			
		}
		System.out.println("Exit program.");
		console.close();
	}
}