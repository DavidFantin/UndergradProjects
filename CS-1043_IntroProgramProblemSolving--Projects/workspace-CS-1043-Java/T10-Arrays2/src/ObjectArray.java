/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/22/18
Assignment: Create an employee array database
Description: This code prints out an employee's name and age
*/

import javax.swing.JOptionPane;
import java.util.Scanner;

public class ObjectArray 
{
	public static void main(String[] args) 
	{
		Scanner console = new Scanner(System.in);
		String prompt = "Enter name and age, as in:  name, age  ";
		final int MAX_NAMES = 500;
		
		Employee[] employeeDB = new Employee[MAX_NAMES];

		String record = new String("");
		Scanner fields = null;
		boolean next = true;
		int nWorkers = 0;
		while (nWorkers < MAX_NAMES && next) 
		{
			System.out.print(prompt);
			// show in the console panel.
			record = JOptionPane.showInputDialog(prompt);
			System.out.println(record);
			// show in the console panel.
			if (record.length() > 0)
			// test for an empty string
			{
				fields = new Scanner(record);
				String name = (fields.useDelimiter(", *")).next();
				if (!fields.hasNext()) 
				{
					System.err.print("Missing token.");
					System.exit(-1);
				}
				double age = fields.nextDouble();
				employeeDB[nWorkers] = new Employee(name, age);
				++nWorkers;	
			} else {
				next = false;
			}
	  }

		for(Employee worker : employeeDB)
		{
			if( worker == null ) break;
			System.out.println(worker.toString());
		}
		
		/* Traditional Way
		 * for ( int ix = 0; ix < nWorkers; ++ix ) 
		 * {
		 *	 System.out.println( employeeDB[ix].toString() );
		 *}
		 */
		
	fields.close();
	console.close();
		
	}
}