/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/8/18
Assignment: Lab 12
Description: Create a ship with a name, crew count and passenger count or capacity
*/

public class Freighter extends Ship {
	private double capacity_;

	public Freighter(String name, int crew, double capacity) {
		super(name, crew);
		capacity_ = capacity;
	}

	public double getCapacity() {
		return capacity_;
	}

	public void setCapacity(double capacity_) {
		this.capacity_ = capacity_;
	}

	public String toString() {
		String Output = super.toString() + "\nThe capacity is: " + getCapacity();
		return Output;
	}
}
