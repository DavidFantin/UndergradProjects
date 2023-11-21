//Name: David Fantin
//ID: 1525813
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 2/20/19
//Assignment: Lab 07

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lab7 {
	// Declares the scanners "I" and "console"
	private static Scanner I;
	private static Scanner console;

	public static void main(String[] args) {
		boolean tryAgain;
		Scanner tokens = null;

		do {
			// Ask for filename to read and evaluate
			console = new Scanner(System.in);
			System.out.printf("Enter file name to read: ");
			String inputFile = console.next();
			tryAgain = false;

			try {

				File fileR = new File(inputFile);
				tokens = new Scanner(fileR);
				String expr = null;

				// Loops through and evaluates/prints each expression in the file
				while (tokens.hasNextLine()) {
					expr = tokens.nextLine();
					int ans = evaluatePostfix(expr);
					// Print the expression and its evaluation on the console
					System.out.println("The expresion: \"" + expr + "\" = " + ans + "\n");
				}
			} catch (FileNotFoundException er) {
				System.out.printf("Error opening file %s, %s\n", inputFile, er);
				tryAgain = true;
			}

		// If the entered file name is incorrect, ask for it to be entered again
		} while (tryAgain);
	}

	public static int evaluatePostfix(String expr) {
		// Create a stack
		StackVectorBased<Integer> stack = new StackVectorBased<Integer>();

		// Scan expression from left to right
		I = new Scanner(expr);

		while (I.hasNext()) {
			// If the next value is an operand, push it into the stack
			if (I.hasNextInt()) {
				stack.push(I.nextInt());
			} else {
				// The pointer moves to the next operator in the expression
				String O = I.next();
				
				// Declares Integers "first" and "second"				
				int second = stack.pop();
				int first = stack.pop();

				// Checks for the different types of operator
				if (O.equals("*")) {
					stack.push(first * second);
				} else if (O.equals("/")) {
					stack.push(first / second);
				} else if (O.equals("+")) {
					stack.push(first + second);
				} else {
					stack.push(first - second);
				}
			}
		}
		// Returns the last number left in the stack, which subsequently is the answer
		return stack.pop();
	}
}