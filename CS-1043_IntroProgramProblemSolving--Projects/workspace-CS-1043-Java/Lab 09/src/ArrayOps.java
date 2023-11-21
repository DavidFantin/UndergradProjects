
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/18/18
Assignment: Make a code that finds the index of the minimum value an array, reverses the array, and finds the index of the minimum value of that array
Description: This code finds the index of the minimum value an array, reverses the array, and finds the index of the minimum value of that array.
*/

public class ArrayOps {
	private int[] data;

	public ArrayOps(int[] input) {
		data = new int[input.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = input[i];
		}

	}

	public String toString() {
		String ArrayOps = new String();
		for (int i = 0; i < data.length; i++) {
			ArrayOps += String.format("data[%d] = %d \n", i, data[i]);
		}
		return ArrayOps;
	}

	public void reverse() {
		for (int i = 0; i < data.length / 2; i++) {
			int temp = data[i];
			data[i] = data[data.length - i - 1];
			data[data.length - i - 1] = temp;
		}
	}

	public int findMin() {
		int IDX = 0;
		int minValue = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] < minValue) {
				minValue = data[i];
				IDX = i;
			}
		}

		return IDX;

	}

}
