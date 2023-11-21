
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/17/18
Assignment: Format things
Description: This code Formats things
*/

import javax.swing.JOptionPane;

public class Loans {

	public static void main(String[] args) {

		String input1 = JOptionPane.showInputDialog("Input the Initial Loan Amount: ");
		double lamount = Double.parseDouble(input1);

		String input2 = JOptionPane.showInputDialog("Input the Low Annual Interest Rate: ");
		double LOWIR = Double.parseDouble(input2);

		String input3 = JOptionPane.showInputDialog("Input the High Annual Interest Rate: ");
		double HIGHIR = Double.parseDouble(input3);

		String LINE1 = "Table of monthly payments for a dollar loan amount of ";
		System.out.printf("%-54s%c%-20.2f\n\n", LINE1, '$', lamount);

		System.out.printf("%-6s%12d%15d%15d%15d\n", "Years:", 15, 20, 25, 30);

		for (double i = 0; i < 66; i++)
			System.out.print("_");

		System.out.println();

		for (double i = LOWIR; i <= HIGHIR; i += .5) {
			System.out.printf("%-3.1f%%", i);
			for (double idx = 15; idx <= 30; idx += 5) {
				double I = i / 100;
				double m = (lamount * (I / 12) * Math.pow((1 + (I / 12)), (idx * 12)))
						/ (Math.pow((1 + (I / 12)), (idx * 12)) - 1);
				System.out.printf("%9c%6.2f", '$', m);
			}
			System.out.println();

		}
	}
}