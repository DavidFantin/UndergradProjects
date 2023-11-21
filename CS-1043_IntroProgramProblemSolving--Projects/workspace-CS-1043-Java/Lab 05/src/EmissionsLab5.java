
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/20/18
Assignment: Write a Java program that interacts with a user by printing out a list of pollutants and requests input from the user 
Description: this program prints out emission limit values and declares if they are above the limit 
*/

import javax.swing.JOptionPane;

public class EmissionsLab5 {
	// Declare two static variables for the first and second mileage
	// limits here:
	final static int FIRST_MILEAGE_LIMIT = 50_000;
	final static int SECOND_MILEAGE_LIMIT = 100_000;

	public static void main(String[] args) {
		// Declare ALL 5 variables here (see instructions):
		String choices = " 1. Carbon Monoxide\n 2. Hydrocarbons\n 3. Nitrogen oxides\n 4. Non-methane hydrocarbons";
		int pollutantNumber = 0;
		int mileage = 0;
		double gramsEmitted = 0.0;
		double firstGramLimit = 0.0;
		double secondGramLimit = 0.0;

		String inputPN = choices + "\n\n Enter Pollutant Number: ";
		System.out.println(inputPN); // console display
		pollutantNumber = Integer.parseInt(JOptionPane.showInputDialog(inputPN));

		String inputGE = "\n Enter Grams of Pollutant Emitted per mile: ";
		System.out.println(inputGE); // console display
		gramsEmitted = Double.parseDouble(JOptionPane.showInputDialog(inputGE));

		String inputM = "\n Enter Odometer Reading: ";
		System.out.println(inputM); // console display
		mileage = Integer.parseInt(JOptionPane.showInputDialog(inputM));

		// Next, get the grams emitted and odometer reading in miles.
		// Create a switch. The switch will assign the first and second gram
		// gram limit based on the pollutant number
		switch (pollutantNumber) {
		case 1:
			firstGramLimit = 3.4;
			secondGramLimit = 4.2;
			break;
		case 2:
			firstGramLimit = .31;
			secondGramLimit = .39;
			break;
		case 3:
			firstGramLimit = .40;
			secondGramLimit = .50;
			break;
		case 4:
			firstGramLimit = .25;
			secondGramLimit = .31;
			break;
		default:
			System.out.println("Pollutant must be within the range (1,4)");
			System.exit(1);
		}

		boolean result = mileageGramsLogic(mileage, gramsEmitted, firstGramLimit, secondGramLimit); // =
																									// mileageGramsLogic(
																									// see
																									// instructions
																									// );
		// Display to the IDE console window and the showMessageDialog box.
		if (result) {
			String BR = "Emissions exceed the permitted level.";
			System.out.println(BR);
			result = Boolean.parseBoolean(JOptionPane.showInputDialog(BR));
		} else {
			String GR = "Emissions are within the permitted level.";
			System.out.println(GR);
			result = Boolean.parseBoolean(JOptionPane.showInputDialog(GR));
		}

	} // end main

	public static boolean mileageGramsLogic(int odometerMileage, double emittedGrams, double firstGramLimit,
			double secondGramLimit) {
		if (odometerMileage > SECOND_MILEAGE_LIMIT) {
			return true;
		} else if (odometerMileage <= FIRST_MILEAGE_LIMIT) {
			if (emittedGrams <= firstGramLimit)
				return true;
			else
				return false;
		} else if (emittedGrams <= secondGramLimit) {
			return true;
		} else
			return false;
	}

}