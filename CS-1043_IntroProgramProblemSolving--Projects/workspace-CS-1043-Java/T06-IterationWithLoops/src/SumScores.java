/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/24/18
Assignment: Write a while loop to determine which input value is the minimum from a list of quiz scores entered by a student.
Description: This program finds the minimum number out of the ones the user types in
*/
import java.util.Scanner;

public class SumScores {
	public static void main(String[] args) 
	{
/*		double minVal = Double.MAX_VALUE;
		Scanner console = new Scanner(System.in);
		double value;
		
		System.out.print("Enter a number or stop to exit: ");
		String input = console.next();
		
	// write a while loop to find the minimum value from a list of
	// numbers input by a user:
		
		while (!input.equalsIgnoreCase("stop"))
		{ value = Double.parseDouble(input);
		  if(value < minVal) 
			  minVal= value;
		  System.out.print("Enter a number or stop to exit: ");
		  input = console.next();
		}
		
		System.out.println("The minimum score is: " + minVal);
		
		/************************************************************/
		
/*		double total = 0.0;
		int count = 0;
		System.out.print("Enter a number or stop to exit: ");
		input = console.next();
		
		while (!input.equalsIgnoreCase("stop"))
		{ value = Double.parseDouble(input);
		  if(value > 0.0)
		  { count ++;
		    total = total + value;
		  }
		System.out.print("Enter a number or stop to exit: ");
		input = console.next();		
		}
		
		if (count > 0)
			System.out.println( "The average score is: " + total/count );
		else
			System.out.println( "No values were entered" );
		/***********************************************************/
	}
	// end main
}