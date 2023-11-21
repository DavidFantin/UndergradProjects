/* 
Name: David Fantin
Lab Section: 2003-L3
Lecture Section: 2003-02
Date: 1/9/19
Assignment: Lab 1
*/

public interface BankAcc {
	public String getName();
	public Integer getAccNum();
	public Float getBalance();
	public Float deposit(Float amount);
	public boolean withdraw(Float amount);
}