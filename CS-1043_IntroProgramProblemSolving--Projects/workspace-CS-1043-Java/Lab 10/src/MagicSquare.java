/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/25/18
Assignment: Lab 10
Description: Test and see if a 2d array is a magic square or not
*/

public class MagicSquare {

	private int[][] square;

	// Deep Copy of the inputs to square[][]
	public MagicSquare(int[][] MS) {
		square = new int[MS.length][];
		for (int i = 0; i < MS.length; i++) {
			square[i] = new int[MS[i].length];
			for (int j = 0; j < MS[i].length; j++) {
				square[i][j] = MS[i][j];
			}
		}
	}

	// To print the array
	public String toString() {
		String MagicSquare = "";
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				MagicSquare += String.format("square[%d][%d] = %d \n", i, j, square[i][j]);
			}
		}
		return MagicSquare;
	}

	// TESTS EACH ARRAY TO SEE IF IT IS A MAGIC SQUARE
	public boolean magicTest() {
		boolean test = false;

		// Test to see if it is even a Square
		for (int i = 0; i < square.length; i++) {
			if (square.length != square[i].length) {
				test = false;
				return test;
			} else {
				test = true;
			}
		}

		// Tests to see if there are multiples of the same number in each row or column
		if (test) {
			// Creates a temp array to hold each digit
			for (int i = 0; i < square.length; i++) {
				for (int j = 0; j < square[i].length; j++) {
					int temp = square[i][j];

					// Compares each digit in the rows and columns with the temp array created above
					for (int I = 0; I < square.length; I++) {
						for (int J = 0; J < square[I].length; J++) {
							if (i != I && j != J) {
								if (temp == square[I][J]) {
									test = false;
									return test;
								} else
									test = true;
							}
						}
					}
				}
			}
		}
		
		// Tests to see if sums are equal
		if (test) {		
			// Sums of Diagonals
			int diagSum = 0;
			for (int i = 0; i < square.length; i++)
				diagSum += square[i][i];

			// Sums of Rows
			for (int i = 0; i < square.length; i++) {
				int rowSum = 0;
				for (int j = 0; j < square[i].length; j++)
					rowSum += square[i][j];

				// Compare Sums of Rows vs Diagonals
				if (rowSum != diagSum)
					return false;
			}
			// Sums of Columns
			for (int i = 0; i < square.length; i++) {
				int colSum = 0;
		 		for (int j = 0; j < square[i].length; j++)
					colSum += square[j][i];

				// Compare Sums of Columns vs Diagonals
				if (colSum != diagSum)
					return false;
			}
		}
		
		return test;
	}
}
