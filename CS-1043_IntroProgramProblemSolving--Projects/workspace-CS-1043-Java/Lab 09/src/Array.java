/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/18/18
Assignment: Make a code that finds the index of the minimum value an array, reverses the array, and finds the index of the minimum value of that array
Description: This code finds the index of the minimum value an array, reverses the array, and finds the index of the minimum value of that array.
*/

import javax.swing.JOptionPane;

public class Array {
	public static void main(String[] args) {
		int[] input = { 1, 9, -2, 8, -4, 7, -5, 3, 6, 0 };

		ArrayOps testObj = new ArrayOps(input);

		System.out.print(testObj + "\n");

		JOptionPane.showMessageDialog(null, "The Index Of The Minimum Value In The Array Is: " + testObj.findMin());
		System.out.print("The Index Of The Minimum Value In The Array Is: " + testObj.findMin() + "\n\n");

		testObj.reverse();
		System.out.print(testObj + "\n");

		JOptionPane.showMessageDialog(null,
				"The Index Of The Minimum Value For The Reversed Array Is: " + testObj.findMin());
		System.out.print("The Index Of The Minimum Value For The Reversed Array Is: " + testObj.findMin() + "\n\n");
	}
}
