
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/10/18
Assignment: 
Description: 
*/
/*
Starter code for the Tax Table 
Tutorial
.
*/
import java.util.Scanner;

public class taxDriver {

	public static void main(String[] args) {

		Scanner console = new Scanner(System.in);


		System.out.print("Enter income: ");
		double income = console.nextDouble();
		if (income < 0.0) income = 0.0;
		
		// M or m for married, s or S for single(use equalsIgnoreCase)
		
		boolean marriageStatus = false;
		System.out.print("Enter marriage status as single or married: ");
		String mStat = console.next();
		
		if (mStat.substring(0,1).equalsIgnoreCase("S")) marriageStatus = true;	
		System.out.println("marriage status = ");
		System.out.println(marriageStatus);
		
		double tax = computeTax(marriageStatus, income);
		System.out.println("Tax is: $" + tax);
	}

	public static double computeTax(boolean mStatus, double income) 
	{
	
		double tax;
		if (mStatus)
		{if (income <= 21450.0)
				tax = income * 0.15;
		else if (income <= 51900.0)
				tax = 3217.50 + (income - 21450.0) * 0.28;
		else	tax = 11743.50 + (income - 51900.0) * 0.31;
		}
		
		else{
		  if (income <= 35800.0)
			    tax = income * 0.5;
		  else if (income <= 51900.0)
				tax = 3217.50 + (income - 35800.0) * 0.28;
		  else	tax = 11743.50 + (income - 86500.0) * 0.31;
		}
		
		return tax;
	}
		
}
