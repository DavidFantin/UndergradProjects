/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/25/18
Assignment: Lab 10
Description: Test and see if a 2d array is a magic square or not
*/

public class MagicSquareDriver {

	public static void main(String[] args) {

		// Arrays to analyze
		int[][][] matrices = { { { 5, 5, 5 }, { 5, 5, 5 }, { 5, 5, 5 } },
							 { { 2, 7, 6 }, { 9, 5, 1 }, { 4, 3, 8 } },
							 { { 15, 1, 14 }, { 9, 10, 11 }, { 6, 19, 5 } },
							 { { 1 }, { 6, 7 }, { 9, 8, 4 } },
							 { { 16, 3, 2, 13 }, { 5, 10, 11, 8 }, { 9, 6, 7, 12 }, { 4, 15, 14, 1 } } };

		// For each 2-D matrix in the array of matrices
		for (int[][] matrix : matrices) {
			MagicSquare table = new MagicSquare(matrix);

			// Prints all of the arrays
			String output1 = table.toString();
			System.out.printf(output1);

			// Performs the magic square test from the MagicSquare.java file
			boolean test = table.magicTest();

			// Prints out the result of the findings from the test above
			if (test)
				System.out.print("The matrix is a magic square\n\n");
			else
				System.out.print("The matrix NOT a magic square\n\n");
			System.out.println();

		}

	}

}
