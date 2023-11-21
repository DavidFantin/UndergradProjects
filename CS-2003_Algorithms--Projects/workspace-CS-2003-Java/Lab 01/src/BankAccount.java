/* 
Name: David Fantin
Lab Section: 2003-L3
Lecture Section: 2003-02
Date: 1/9/19
Assignment: Lab 1
*/

public class BankAccount implements BankAcc, Comparable<BankAccount> {
	Float balance;
	Integer accNum;
	String customerName;
	static Integer customerNum = 0;

	public BankAccount(String name) {
		balance = 0.0f;
		customerName = name;
		accNum = ++customerNum;
		System.out.println("Account #" + accNum + " created for customer " + name + " with initial balance " + balance);
	}

	public Integer getAccNum() {
		return accNum;
	}

	public Float getBalance() {
		return balance;
	}

	public String getName() {
		return customerName;
	}

	public int compareTo(BankAccount b) {
		return balance.compareTo(b.getBalance());
	}

	public Float deposit(Float amt) {
		balance += amt;
		System.out.printf("Depositing " + amt + " to account #" + accNum + ", new balance: %.2f \n", balance);
		return balance;
	}

	public boolean withdraw(Float amt) {
		System.out.print("Withdrawing " + amt + " from account #" + accNum);
		if (amt > balance) {
			System.out.println(", withdrawal denied: requested amount exceeds balance!");
			return false;
		} else {
			balance -= amt;
			System.out.printf(", new balance: %.2f \n", balance);
			return true;
		}
	}

	// NOTE: Following method is additional functionality and
	// not part of any of the interfaces implemented by this class
	// NOTE: Use of interface for specifying type of function parameter!
	public boolean transfer(Float amt, BankAcc b) {
		System.out.println("Transferring " + amt + " from account #" + accNum + " to account# " + b.getAccNum()
				+ " by the following operations:");
		if (amt <= 0) {
			System.out.println("Invalid transfer amount " + amt);
			return false;
		} else if (amt > balance) {
			System.out.println("Insufficient balance " + balance + " for transferring " + amt);
			return false;
		} else {
			System.out.print("   ");
			b.deposit(amt);
			System.out.print("   ");
			return withdraw(amt);
		}
	}
} // end class