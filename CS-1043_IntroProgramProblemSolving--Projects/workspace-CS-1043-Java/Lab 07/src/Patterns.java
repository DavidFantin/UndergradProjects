
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/4/18
Assignment: Print an asterisk hour glass 
Description: This code prints an asterisk hour glass
*/

import javax.swing.JOptionPane;

public class Patterns {

	public static void main(String[] args) {

		String prompt = "Enter the Number of Rows in the Hourglass";
		String inputVal = JOptionPane.showInputDialog(prompt);

		int N = Integer.parseInt(inputVal);
		int num = N;
		int stars = N;
		int Stars;

		// Condition
		if (N < 0 || N % 2 == 0) {
			System.out.print("Input cannot be resolved");
			System.exit(1);
		}

		// Top
		for (int i = 0; i < (num + 1) / 2; i++) {
			for (int j = 0; j < i; j++)
				System.out.print(" ");

			for (int j = 0; j < stars; j++)
				System.out.print("*");

			System.out.println();
			stars -= 2;
		}
		// Bottom
		for (int I = ((num - 1) / 2); I > 0; I--) {
			Stars = num + 2 - 2 * I;
			for (int j = 0; j < I - 1; j++)
				System.out.print(" ");

			for (int j = 0; j < Stars; j++)
				System.out.print("*");

			System.out.println();
		}

	}
}