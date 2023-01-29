/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/10/18
Assignment: Print an asterisk bowtie 
Description: This code prints an asterisk bowtie
*/


import javax.swing.JOptionPane;

public class bowtie {

	public static void main(String[] args) {

		String prompt = "Enter the Number of Rows in the Bowtie ";

		String inputVal = JOptionPane.showInputDialog(prompt);

		int nRows = Integer.parseInt(inputVal);

		if (nRows < 0 || nRows % 2 == 0) {
			System.out.print("Input cannot be resolved");
			System.exit(1);
		}

		System.out.println("");

		for (int i = 1; i <= (nRows + 1) / 2; i++) {
			int stars = i;
			int blanks = nRows + 1 - 2 * stars;

			for (int j = 0; j < stars; j++)
				System.out.print("*");

			for (int j = 0; j < blanks; j++)
				System.out.print(" ");

			for (int j = 0; j < stars; j++)
				System.out.print("*");

			System.out.println("");
		}
		for (int i = (nRows - 1) / 2; i > 0; i--) {
			int stars = i;
			int blanks = nRows + 1 - 2 * stars;
			for (int j = 0; j < stars; j++)
				System.out.print("*");

			for (int j = 0; j < blanks; j++)
				System.out.print(" ");

			for (int j = 0; j < stars; j++)
				System.out.print("*");

			System.out.println("");
		}

	}
}
