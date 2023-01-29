/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/8/18
Assignment: Lab 12
Description: Create a ship with a name, crew count and passenger count or capacity
*/

public class Liner extends Ship {

	private int passengers_;

	public Liner(String name, int crew, int passengers) {
		super(name, crew);
		passengers_ = passengers;
	}

	public int getPassengers() {
		return passengers_;
	}

	public void setPassengers(int passengers) {
		this.passengers_ = passengers;
	}

	public String toString() {
		String Output = super.toString() + "\nThe number of passengers onbord is: " + getPassengers();
		return Output;
	}
}