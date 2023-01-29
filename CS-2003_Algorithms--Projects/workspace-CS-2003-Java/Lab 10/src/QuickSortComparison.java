
//Name: David Fantin
//ID: 1525813
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 3/13/19
//Assignment: Lab 10

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
/* Lab designed to study and compare runtimes of iterative and recursive
 * versions of QuickSort algorithm
 */

public class QuickSortComparison {
	static int stack[];
	static int top;

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

	/**
	 * Calls recursive or iterative quicksort functions based on flag variable
	 * isIterative
	 * 
	 * @param array
	 *            to be sorted
	 * @param low
	 *            first index of array
	 * @param high
	 *            last index of array
	 * @param isIterative
	 *            flag variable
	 */
	public static <E extends Comparable<? super E>> void quickSort(E[] array, int low, int high, boolean isIterative) {
		if (isIterative)
			quickSortIterative(array, low, high);
		else
			quickSortRecursive(array, low, high);
	}

	/**
	 * Performs quicksort with recursion
	 * 
	 * @param array
	 *            to be sorted
	 * @param low
	 *            first index of array slice being sorted
	 * @param high
	 *            last index of array slilce being sorted
	 */
	private static <E extends Comparable<? super E>> void quickSortRecursive(E[] array, int low, int high) {
		if (low < high) {
			int pi = partition(array, low, high);
			quickSortRecursive(array, low, pi - 1);
			quickSortRecursive(array, pi + 1, high);
		}
	}

	/**
	 * pushes item into stack
	 * 
	 * @param item
	 *            to be pushed
	 */
	private static void push(int item) {
		stack[++top] = item;
	}

	/**
	 * pops the last item in the stack
	 * 
	 * @return last item in stack
	 */
	private static int pop() {
		return stack[top--];
	}

	/**
	 * Performs quickSort using iteration with the help of a stack
	 * 
	 * @param array
	 *            to be sorted
	 * @param low
	 *            first index of array slice being sorted
	 * @param high
	 *            last index of array slilce being sorted
	 */
	private static <E extends Comparable<? super E>> void quickSortIterative(E[] array, int low, int high) {
		top = -1;
		stack = new int[(high - low) + 1];
		push(low);
		push(high);
		while (top > -1) {
			high = pop();
			low = pop();
			int pivot_index = partition(array, low, high);
			if (pivot_index - 1 > low) {
				push(low);
				push(pivot_index - 1);
			}
			if (pivot_index + 1 < high) {
				push(pivot_index + 1);
				push(high);
			}
		}
	}

	public static void main(String args[]) {
		int arraySizes[] = { 100, 1000, 10000, 100000, 1000000 };
		int nIterations = 10;
		long avgRunTimesIter[] = new long[arraySizes.length];
		long avgRunTimesRec[] = new long[arraySizes.length];
		Random randomNumberGenerator = new Random(101);
		for (int i = 0; i < arraySizes.length; i++) {
			int iSize = arraySizes[i];
			Integer arrayOrig[][] = new Integer[100][iSize];
			for (int k = 0; k < nIterations; k++) {
				for (int idx = 0; idx < iSize; idx++) {
					arrayOrig[k][idx] = randomNumberGenerator.nextInt(100);
				}
				Integer arrayCopy1[] = new Integer[iSize];
				Integer arrayCopy2[] = new Integer[iSize];
				for (int idx = 0; idx < iSize; idx++) {
					arrayCopy1[idx] = arrayOrig[k][idx];
					arrayCopy2[idx] = arrayOrig[k][idx];

				}
				// Calculate Iterative quick sort run time
				long startTime1 = System.nanoTime();
				quickSort(arrayCopy1, 0, arrayCopy1.length - 1, true);
				avgRunTimesIter[i] += System.nanoTime() - startTime1;
				// Calculate Recursive quick sort run time
				long startTime2 = System.nanoTime();
				quickSort(arrayCopy2, 0, arrayCopy2.length - 1, false);
				avgRunTimesRec[i] += System.nanoTime() - startTime2;
			}
			System.out.println("Average Runtime for Array size: " + iSize + ":");
			System.out.println("Iterative   : " + avgRunTimesIter[i] / nIterations);
			System.out.println("Recursive : " + avgRunTimesRec[i] / nIterations);
		}
	}
}
