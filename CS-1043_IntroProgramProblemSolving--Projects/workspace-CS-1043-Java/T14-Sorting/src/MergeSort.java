/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/26/18
*/

public class MergeSort 
{
	public static void main(String[] args) 
	{
		int[] data = { 19, 29, 5, 73, 43, 97, 23, 59 };
		mergeSort(data, 0, data.length - 1);
		for (int xval : data) 
		{
			System.out.printf("%8d", xval);
		}
		System.out.println();
	}

	/*
	 * mergeSort is a recursive method used to sort data.
	 * 
	 * Input a n integer array to sort ; and , the fir stand last array indices.
	 */
	public static void mergeSort(int values[], int start, int end) 
	{
		if (start < end) // not the base case
		{
			int mid = (start + end) / 2; // reduction
			mergeSort(values, start, mid); // recursion
			mergeSort(values, mid + 1, end); // recursion
			merge(values, start, mid, end); // merge the two sorted halves.
		}
		// base case: otherwise there is nothing to sort!
	} // end recursive mergeSort
	/*
	 * merge is an iterative process used to merge two sorted lists together .
	 *
	 * This method is used with the recursive mergeSort method.
	 *
	 * Input s The array divided into two properly sorted halves .
	 * 
	 * The first half of the array starting at index start and ending at
	 * 
	 * mid - 1 must be sorted; the second half of the array starting at mid
	 * 
	 * and ending at end must also be sorted.
	 */

	public static void merge(int values[], int start, int mid, int end) 
	{
		int n = end - start + 1;
		int[] tmp = new int[n]; // create a temporary storage array.
		int i1 = start; // index for first half.
		int i2 = mid + 1; // index for second half.
		int j = 0; // index for the tmp array;
		while (i1 <= mid && i2 <= end) // move data and pointers
		{
			if (values[i1] < values[i2]) 
			{
				tmp[j] = values[i1];
				++i1; // advance the left side pointer.
			} else {
				tmp[j] = values[i2];
				++i2; // advance the right side pointer.
			}
			++j; // advance the tmp array pointer
		} // end while
		
		// only one of the two remaining while loops will execute.
		while (i1 <= mid) // merge remaining left-side values.
		{
			tmp[j] = values[i1];
			++i1;
			++j;
		} // end while
		while (i2 <= end) // merge remaining right-side values.
		{
			tmp[j] = values[i2];
			++i2;
			++j;
		} // end while
		
		// Copy sorted tmp data back to the original input array.
		for (j = 0; j < n; j++) 
		{
			values[start + j] = tmp[j];
		}
	} // end merge method
}
