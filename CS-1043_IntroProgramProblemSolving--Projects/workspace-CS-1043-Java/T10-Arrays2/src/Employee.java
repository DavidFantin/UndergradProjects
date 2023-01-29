/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/22/18
Assignment: Create an employee array database
Description: This code prints out an employee's name and age
*/

public class Employee {
	
	private String name;
	private double age;

	public Employee(String name, double age){
		super();
		this.name = name;
		this.age = age;
	}
	
	public String toString(){
		return "Employee [name = " + name + ", age = " + age + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	
} // end of the Employee object class (Employee template class)