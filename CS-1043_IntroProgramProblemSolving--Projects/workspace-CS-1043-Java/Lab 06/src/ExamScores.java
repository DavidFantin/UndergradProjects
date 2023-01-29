/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/27/18
Assignment: Write a program that reads a series of test scores from a recent test (integers in the range 0 to 100) until a sentinel value is read.
Description: This program reads a series of test scores from a recent test (integers in the range 0 to 100) until a sentinel value is read.
*/

import javax.swing.JOptionPane;
//
// Import the class that holds the min and max integer values.
public class ExamScores {
	public static void main(String args[]) {
		// Declare and initialize your variables:
		int count = 0;
		int total = 0;
		double Average;
		double dmin = Double.MAX_VALUE;
		double dmax = Double.MIN_VALUE;
		

		while(true){
			
			String prompt = "Enter a number or stop to end the program.";
			
			String inputVal = JOptionPane.showInputDialog(prompt);
			
			if(inputVal.equalsIgnoreCase("stop") || inputVal.equalsIgnoreCase("exit"))
				break;
				
			System.out.println(prompt);
			
			int value = Integer.parseInt(inputVal);
			System.out.println(value);
			

			// Here is where you sum, count and test for the
			// Min and Max values.
			// Now prompt for the next value:
			if (value < 0) {
				String errPrompt = "Number must be at least 0, but not greater than 100";
				System.out.println(errPrompt);
				JOptionPane.showMessageDialog(null, errPrompt);
			} else if (value <= 100) {
				count++;
				total += value;
				if (value < dmin)
					dmin = value;
				if (dmax < value)
					dmax = value;
			} else {
				String errPrompt = "Number must be at least 0, but not greater than 100";
				System.out.println(errPrompt);
				JOptionPane.showMessageDialog(null, errPrompt);
			}
		}

		Average = total/count;
		
		String outputMSG = "The total sum is " + total + "\n" + "The count is " + count + "\n" + "The Max is " + dmax + "\n" + "The Min is " + dmin + "\n" + "The average of the numbers is "+ Average;
		System.out.println(outputMSG);
		JOptionPane.showMessageDialog(null, outputMSG);

	}
}