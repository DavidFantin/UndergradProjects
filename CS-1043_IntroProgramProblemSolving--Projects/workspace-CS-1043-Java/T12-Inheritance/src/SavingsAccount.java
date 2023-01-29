/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

public class SavingsAccount extends BankAccount {
	public SavingsAccount(double rate) {
		super();
		interestRate_ = rate;
	} // end constructor

	public void addInterest() {
		double interest = getBalance() * interestRate_ / 100.0;
		deposit(interest);
	}

	private double interestRate_;

} // end the SavingsAccount class