import java.util.Scanner;

public class Conversions {

	public static void main(String[] args) {
		
		//MILES to KM
		final double MILES2KM = 1.609;
		Scanner console = new Scanner( System.in);
		System.out.print("Enter a distance in miles: ");
		double miles = console.nextDouble();
		double km = MILES2KM * miles;
		System.out.println(miles + " miles is " + km + " kilometers");

		//KM to MILES
		System.out.print("Enter a distance in kilometers: ");
		km = console.nextDouble();
		miles = KmToMiles(km);
		System.out.println(km + " kilometers is " + miles + " miles");
		
		//KM to NAUTICAL MILES
		System.out.print("Enter a distance in kilometers: ");
		km = console.nextDouble();
		double nmiles = KmToNMiles(km);
		System.out.println(km + " kilometers is " + nmiles + " nautical miles");
		
	}
	
	public static double KmToMiles(double km){
		
		final double KM2MILES = 1.00/1.609;
		return km * KM2MILES;

	}
	
	public static double KmToNMiles(double km){
		
		final double Miles2NMiles = 1.00/1.1508;
		return KmToMiles(km) * Miles2NMiles;
		
	}
}
