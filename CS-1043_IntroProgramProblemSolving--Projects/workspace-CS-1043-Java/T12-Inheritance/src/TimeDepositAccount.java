/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

public class TimeDepositAccount extends SavingsAccount {
	public TimeDepositAccount(double rate, int maturity) {
		super(rate);
		periodsToMaturity_ = maturity;
	}

	public void addInterest() {
		--periodsToMaturity_;
		super.addInterest();
	}

	public void withdraw(double amount) {
		if (periodsToMaturity_ > 0)
			super.withdraw(EARLY_WITHDRAWAL_PENALTY);
		super.withdraw(amount);
	}

	private int periodsToMaturity_;

	final static double EARLY_WITHDRAWAL_PENALTY = 20;

} // end TimeDepositAccount class