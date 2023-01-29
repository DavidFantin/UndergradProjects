/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/12/18
*/

import java.util.Scanner;

public class Fibonacci 
{
	public static long count = 0;
	
	public static void main(String args[]) 
	{
		final double NORM = 1e-9; // converts nanoTime to seconds.
		
		Scanner console = new Scanner(System.in);
		System.out.print("Enter a number for Fibonacci number calculation: ");
		int nth = console.nextInt();
		
		System.out.println(
				"\nFirst compute the nth Fibonacci number using Iteration.");
		long startTime = System.nanoTime();
		long fibn = fibI(nth);
		long tTime = System.nanoTime() - startTime;
		System.out.printf("Iteration: The %d Fibonacci number is %d\n", nth, fibn);
		System.out.printf("Iteration time: %g sec.\n", (tTime * NORM));
		
		System.out.println(
				"\nNow compute the nth Fibonacci number using Recursion.");
		startTime = System.nanoTime();
		fibn = fibR(nth);
		tTime = System.nanoTime() - startTime;
		System.out.printf("Recursion: The %d Fibonacci number is %d\n", nth, fibn);
		System.out.printf("Recursion time: %g sec.\n", (tTime * NORM));
	}

	public static long fibI(int nth) // Fibonacci using Iteration
	{
		if ( nth < 1 )
			return 0;
		else {
			long fn = 1;
			long fnm1 = 1;
			long fnm2 =1;
			
			for ( int ix = 3; ix <= nth; ix++ )
			{
				fn = fnm1 + fnm2;
				fnm2 = fnm1;
				fnm1 = fn;
			}
			return fn;
		}
	}

	public static long fibR(int nth) // Fibonacci using Recursion
	{
		//count how many times fibR is called
		count ++;
		if (count % 1_000_000_000 == 0)
			System.out.println("Number of fibR calls (in billions) = "
											+ count/1_000_000_000);
			if ( nth < 1)
				return 0;
			else if ( nth ==1 || nth == 2 )
				return 1;
			else
				return fibR( nth-1 ) + fibR( nth-2 );
	}
}