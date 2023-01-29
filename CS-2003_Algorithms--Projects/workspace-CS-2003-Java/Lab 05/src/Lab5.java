
//Name: David Fantin
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 2/8/19
//Assignment: Lab 05

import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.Vector;

public class Lab5 {
	/**
	 * Partitions an array for quickSort.
	 * 
	 * @param first
	 *            is the index of the first element to sort with
	 *            <code>first <= last</code>.
	 * @param last
	 *            is the index of the last element to sort with
	 *            <code>first <= last</code>.
	 * @param theArray
	 *            is the array to be sorted: the element between
	 *            <code>first</code> and <code>last</code> (with
	 *            <code>first <= last</code>)will be sorted.
	 * @return the index of the pivot element of theArray[first..last]. Upon
	 *         completion of the method, this will be the index value lastS1
	 *         such that <code>S1 =
	 * theArray[first..lastS1-1] < pivot theArray[lastS1] == pivot S2 =
	 * theArray[lastS1+1..last] >= pivot </code>
	 */
	private static <E extends Comparable<? super E>> int partition(E[] theArray, int first, int last) {
		// tempItem is used to swap elements in the array
		E tempItem;
		E pivot = theArray[first]; // reference pivot
		// initially, everything but pivot is in unknown
		int lastS1 = first; // index of last item in S1
		// move one item at a time until unknown region is empty
		for (int firstUnknown = first + 1; firstUnknown <= last; ++firstUnknown) {
			// Invariant: theArray[first+1..lastS1] < pivot
			// theArray[lastS1+1..firstUnknown-1] >= pivot
			// move item from unknown to proper region
			if (theArray[firstUnknown].compareTo(pivot) < 0) {
				// item from unknown belongs in S1
				++lastS1;
				tempItem = theArray[firstUnknown];
				theArray[firstUnknown] = theArray[lastS1];
				theArray[lastS1] = tempItem;
			} // end if
				// else item from unknown belongs in S2
		} // end for
			// place pivot in proper position and mark its location
		tempItem = theArray[first];
		theArray[first] = theArray[lastS1];
		theArray[lastS1] = tempItem;
		return lastS1;
	} // end partition

	public static <E extends Comparable<? super E>> E kSmall(int k, E[] array, int first, int last) {
		int pi = partition(array, first, last);
		if ((pi - first + 1) == k) {
			return array[pi];
		} else {
			if ((pi - first + 1) > k) {
				return kSmall(k, array, first, pi - 1);
			} else {
				return kSmall(k - (pi - first + 1), array, pi + 1, last);
			}
		}
	}

	public static void main(String[] args) {
		try {
			int k;
			int count = 0;
			Scanner console = new Scanner(System.in);
			System.out.println("Enter the name of the file containing the data");
			String filename = console.next();
			// read the data in the file
			Vector<Integer> vec = new Vector<Integer>();
			Scanner scanData = new Scanner(new File(filename));
			while (scanData.hasNext())
				vec.add(scanData.nextInt());
			scanData.close();
			Integer[] myArray = new Integer[vec.size()];
			System.out.println("The integers in the file " + filename + " are: ");
			for (Integer val : vec) {
				myArray[count] = val;
				System.out.print(val + " ");
				count++;
			}
			do {
				System.out.print("\nEnter an index: ");
				k = console.nextInt();
				if (k >= 1 && k <= count - 1) {
					int KS = kSmall(k, myArray, 1, count - 1);
					System.out.println("The kth smallest element is: " + KS);
				} else {
					System.out.print("OUT OF BOUNDS ERROR. TRY AGAIN.");
					break;
				}
			} while (k >= 1 && k <= count - 1);
		}

		catch (IOException e) {
			System.err.println("IOError!!!\n" + e);
			System.exit(9);
		}
	}
}// end class