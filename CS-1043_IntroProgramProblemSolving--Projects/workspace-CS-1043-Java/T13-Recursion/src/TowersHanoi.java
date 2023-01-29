/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/12/18
*/

import java.util.Scanner;

public class TowersHanoi 
{
	public static void main(String[] args) 
	{
		Scanner console = new Scanner(System.in);
		System.out.print("Enter the number of disks: ");
		int ndisks = console.nextInt();
		
		long count = towers(ndisks, "Post A", "Post B", "Post C");
		System.out.printf("Number of times towers was called = " + count);
	}

	public static int towers(int disks, String A, String B, String C) 
	{
		if (disks == 1) // base case is one disk
		{
			System.out.printf("move disk from %s to %s\n", A, C);
			//for larger quantities of disks you may want to comment out the above statement
			return 1;
		} 
		else // Add logic to move the other disks recursively
		{
			return ( towers( disks-1, A, C, B ) +
					 towers( 1, A, B, C ) 		+
					 towers( disks-1, B, A, C )    );
		}
	}
}