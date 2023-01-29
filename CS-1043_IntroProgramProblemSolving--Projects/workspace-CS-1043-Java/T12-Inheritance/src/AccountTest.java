/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

/* This program is an exercise for Inheritance and Polymorphism.    
 * Label each method call as either:  
 * 1. Polymorphism,  
 * 2. Inheritance, or 
 * 3. Overloading.  
 * */

public class AccountTest {
	public static void main(String[] args) {

		SavingsAccount momsSavings = new SavingsAccount(0.5);

		TimeDepositAccount collegeFund = new TimeDepositAccount(1, 3);
		CheckingAccount harrysChecking = new CheckingAccount(0.);

		momsSavings.deposit(10000.);
		collegeFund.deposit(10000.);

		momsSavings.transfer(harrysChecking, 2000.);
		collegeFund.transfer(harrysChecking, 980.);

		harrysChecking.withdraw(500.);
		harrysChecking.withdraw(80.);
		harrysChecking.withdraw(400.);
		harrysChecking.withdraw(10000.);

		endOfMonth(momsSavings);
		endOfMonth(collegeFund);
		endOfMonth(harrysChecking);

		printBalance("mom's savings", momsSavings); // $10000 - $2000 + 0.5%
		// = $8040									// interest 
		
		printBalance("the college fund", collegeFund); // $10000 - $980 - $20
		// = $9090									   // penalty + 1% interest
		
		printBalance("Harry's checking", harrysChecking); // $2000 + $980 - $500
		// = $1969										  // - $80 - $400 - $6
														  // fees - 25 penalty
	}

	public static void endOfMonth(SavingsAccount savings) {
		savings.addInterest();
	}

	public static void endOfMonth(CheckingAccount checking) {
		checking.deductFees();
	}

	public static void printBalance(String name, BankAccount account) {
		System.out.println("The balance of " + name + " account is $" + account.getBalance());
	}
}