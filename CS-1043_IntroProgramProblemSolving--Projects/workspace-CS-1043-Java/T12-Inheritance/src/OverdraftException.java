/*
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/05/18
*/

// Customized User (Programmer) Designed Exception Class: 

public class OverdraftException extends RuntimeException {
	public OverdraftException(String reason) {
		super(reason);
	}
}