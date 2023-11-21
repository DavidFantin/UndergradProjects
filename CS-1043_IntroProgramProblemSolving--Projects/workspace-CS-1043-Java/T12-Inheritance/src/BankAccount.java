/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

public class BankAccount {
	private double balance_;
	private long accountNumber_;

	static double OVERDRAFT_PENALTY = 25.0;
	static String bankName = "Wells Fargo Bank";

	public BankAccount() {
		balance_ = 0.;
	} // default (no-arg) constructor.

	public BankAccount(double initialBalance) {
		balance_ = initialBalance;
	}

	public void deposit(double amount) {
		balance_ += amount;
	}

	public void withdraw(double amount) {
		try {
			if (amount > balance_) { // overdraft!
				if (OVERDRAFT_PENALTY > balance_)
					balance_ = 0.0;
				else
					balance_ -= OVERDRAFT_PENALTY;
				throw new OverdraftException("Withdrawal exceeds balance");
			} else {
				balance_ -= amount;
			}

		} catch (OverdraftException e) {
			System.out.println(e);
		}
	} // end withdraw method

	public double getBalance() {
		return balance_;
	}

	public void transfer(BankAccount other, double amount) {
		this.withdraw(amount); // this is the implicit parameter
		other.deposit(amount);
	}
} // end BankAccount class