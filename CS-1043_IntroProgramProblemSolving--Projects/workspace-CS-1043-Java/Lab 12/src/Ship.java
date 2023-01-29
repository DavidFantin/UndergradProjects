/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/8/18
Assignment: Lab 12
Description: Create a ship with a name, crew count and passenger count or capacity
*/

public class Ship {
	private int crew_;
	private String name_;

	public Ship(String name, int crew) {
		name_ = name;
		crew_ = crew;
	}

	public int getCrew() {
		return crew_;
	}

	public void setCrew_(int crew_) {
		this.crew_ = crew_;
	}

	public String getName() {
		return name_;
	}

	public void setName(String name_) {
		this.name_ = name_;
	}

	public String toString() {
		String OUTPUT = "The name of the ship is: " + name_ + "\nThe crew count is: " + crew_;
		return OUTPUT;
	}
}
