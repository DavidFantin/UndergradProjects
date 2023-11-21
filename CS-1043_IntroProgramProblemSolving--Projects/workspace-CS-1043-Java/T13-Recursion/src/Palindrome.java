/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/12/18
*/

import java.util.Scanner;

public class Palindrome 
{
	public static void main(String[] args) 
	{
		String aString;
		while (true) {
			System.out.print("Please enter a string: ");
			Scanner console = new Scanner(System.in);
			aString = console.nextLine();
			if (aString.length() < 1) break;
			
					backw1(aString);
					System.out.println();
		}
	}

	/* Use recursion to write a string backward */
	
	public static void backw1(String str) 
	{
		int strL = str.length();
		
		
		if ( strL > 1 )
			backw1( str.substring( 1, strL ) );
			// print the previous chars backward
		
		System.out.print( str.charAt(0) );
			// print the first char last.
	}
}