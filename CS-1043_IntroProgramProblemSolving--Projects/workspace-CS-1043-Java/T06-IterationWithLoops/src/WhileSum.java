/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/24/18
Assignment: Write a while-loop to sum the even numbers between 2 and 10 (2+4+6+8+10).
Description: This program adds up the even numbers between 2 and 10.
*/
public class WhileSum 
{
	public static void main(String[] args) 
	{
		int sentinel = 10;
		int idx = 2;
		int total = 0; // this is an accumulator
		
	// Write a while-loop to sum the even number from 
	// idx = 2 to sentinel = 10
		
		while (idx <= sentinel)
		{ total = total +idx;
		  idx = idx + 2;
		}
		
		System.out.println( "Total = " + total);
		System.out.println( "idx = " + idx);
	}
}