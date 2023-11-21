/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 10/8/18
Assignment: Display aspects of a circle
Description: This code displays aspects of a circle
*/

public class Circle {
	
	private double radius;
	public Circle() {radius = 1.0;}// unit circle
	public Circle(double rad) {radius = rad;}
	public Circle(Circle input) {radius = input.getRadius();}
	public double getRadius() {return radius;}
	public void setRadius(double rad) {radius = rad;}
	public String toString() 
		{
		String str = String.format( "Circle radius = %-12.4g\n", radius);
		str = str + String.format( "Circle diameter = %-12.4f\n", 2.0 * radius);
		str = str + String.format( "Circle area = %-12.4f\n",
								Math.PI * radius * radius);
		str = str + String.format( "Circle circumference = %-12.4f\n",
								Math.PI * 2.0 * radius);
	
		return str;
}
	// end toString
}
// end Circle class