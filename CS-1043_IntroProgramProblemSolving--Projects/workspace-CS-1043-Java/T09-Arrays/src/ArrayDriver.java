import java.util.Scanner;

public class ArrayDriver {
	public static void main(String[] args) {
		int[] input = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };
		
		ArrayDB dataBase = new ArrayDB(input); // create the database
		
		System.out.println("The unmodified array is: ");
		System.out.print(dataBase.toString()); // Display the database
		
		Scanner console = new Scanner(System.in);
		System.out.print("Enter a pair of numbers, as in 'value, index' : ");
		String record = console.nextLine();
		Scanner fields = new Scanner(record).useDelimiter(", *");
		int value = fields.nextInt();

		int index = fields.nextInt();

		if (dataBase.insertItem(index, value)) {
			System.out.println("The modified array is: ");
			System.out.print(dataBase.toString());
		} else {
			System.out.println("Preconditions not met, no change.");
		}
		System.out.println("Hit enter to continue.");
		record = console.nextLine();
		System.out.print("Remove the data item at this index location: ");
		index = console.nextInt();
		if (dataBase.removeItem(index)) {
			System.out.println("Final result: ");
			System.out.print(dataBase.toString());
		} else
			System.out.println("Preconditions not met, no change.");
		console.close();
	}
	
}

// end the
