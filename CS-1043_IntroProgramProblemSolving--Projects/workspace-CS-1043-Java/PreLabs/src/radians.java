/* Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 8/29/18
Assignment: Convert degrees to radians
Description: this program converts degrees to radians
*/

import java.util.Scanner;

public class radians {

	public static void main(String[] args) 
	{
		
		Scanner console = new Scanner( System.in );
		System.out.print( "Please enter a degree: " );
		double DEG = console.nextDouble();
		
		double RAD = radians.D2RAD( DEG );
		
		System.out.println( "\n" + DEG + " degrees  is equal to " + RAD + " radians. \n" );
		

	}

	public static double D2RAD( double DEG ) 
	{
		double radians = DEG * Math.PI / 180;
		return radians;
	}
}
