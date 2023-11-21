
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/8/18
Assignment: Lab 12
Description: Create a ship with a name, crew count and passenger count or capacity
*/

import java.util.Scanner;
import javax.swing.JOptionPane;

public class LabShipsDriver {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);

		String name = JOptionPane.showInputDialog("Please enter ship name: ");

		int crew = Integer.parseInt(JOptionPane.showInputDialog("Please enter the crew complement: "));

		String type = JOptionPane.showInputDialog(
				"Is the ship a Freighter or a Liner?\n" + "Enter 'F' for Freighter, or 'L' for Liner: ");

		if (type.equalsIgnoreCase("f")) // for a ship of type: Freighter.
		{
			double capacity = Integer
					.parseInt(JOptionPane.showInputDialog("Please enter your freight capacity in tons: "));

			Freighter fship = new Freighter(name, crew, capacity);
			System.out.print(fship.toString());

			System.out.printf("\n\nPlease change the crew number: ");
			int newCrew = console.nextInt();
			crew = newCrew;

			System.out.printf("Please change the freight capacity: ");
			double newCapacity = console.nextInt();
			capacity = newCapacity;

			Freighter fship2 = new Freighter(name, crew, capacity);
			System.out.print(fship2.toString());

		} else if (type.equalsIgnoreCase("l")) {
			int passengers = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of passengers: "));

			Liner lship = new Liner(name, crew, passengers);
			System.out.print(lship.toString());

			System.out.printf("\n\nPlease change the crew number: ");
			int newCrew = console.nextInt();
			crew = newCrew;

			System.out.printf("Please change the number of passengers: ");
			int newPassengers = console.nextInt();
			passengers = newPassengers;

			Liner lship2 = new Liner(name, crew, passengers);
			System.out.print(lship2.toString());

		} else {
			System.out.print("You must input either 'F' or 'L'\nplease try again");
			console.close();
		}
	}
}