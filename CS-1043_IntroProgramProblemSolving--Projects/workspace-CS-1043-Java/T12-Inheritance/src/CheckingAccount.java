/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

public class CheckingAccount extends BankAccount {
	public CheckingAccount(double initialBalance) { // construct superclass
		super(initialBalance);
		
		// initialize transaction count
		transactionCount_ = 0;
	} // end the constructor

	public void deposit(double amount) {
		++transactionCount_; // now add amount to balance
		super.deposit(amount);
	}

	public void withdraw(double amount) {
		++transactionCount_; // now subtract amount from balance
		super.withdraw(amount);
	}

	public void deductFees() {
		if (transactionCount_ > FREE_TRANSACTIONS) {
			double fees = TRANSACTION_FEE * (transactionCount_ - FREE_TRANSACTIONS);
			super.withdraw(fees);
		}
		transactionCount_ = 0;
	} // end deductFees method

	private int transactionCount_;

	static final int FREE_TRANSACTIONS = 3;
	static final double TRANSACTION_FEE = 2.0;

} // end CheckingAccount class