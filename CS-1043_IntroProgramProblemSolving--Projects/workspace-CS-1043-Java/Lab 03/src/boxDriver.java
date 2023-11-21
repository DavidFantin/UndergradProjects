/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/6/18
Assignment: create boxes
Description: this code create boxes and spits out the properties of them
*/

public class boxDriver {

	public static void main(String[] args) {
		
		Box box1 = new Box( ); // this references the no-arg constructor
		Box box2 = new Box( 20.0, 30.0, 40.0, "green" );
		Box box3 = new Box( 18.0, 36.0, 18.0, "blue" );

		Box box4 = new Box(box3);
		
		box4.setColor("red");
		
		box1.setWidth(24);
		

		//area
		System.out.println(box1.getColor() + ": The surface area of box 1 is: " + box1.Area());
		System.out.println(box2.getColor() + ": The surface area of box 2 is: " + box2.Area());
		System.out.println(box3.getColor() + ": The surface area of box 3 is: " + box3.Area());
		System.out.println(box4.getColor() + ": The surface area of box 4 is: " + box4.Area());
		
		//total area
		System.out.println("\nThe combined suface area of all boxes is: " + (box1.Area() + box2.Area() + box3.Area() + box4.Area()));
		
		
		//volume
		System.out.println("\n" + box1.getColor() + ": The volume of box 1 is: " + box1.Volume());
		System.out.println(box2.getColor() + ": The volume of box 2 is: " + box2.Volume());
		System.out.println(box3.getColor() + ": The volume of box 3 is: " + box3.Volume());
		System.out.println(box4.getColor() + ": The volume of box 4 is: " + box4.Volume());
		
		//total volume
		System.out.println("\nThe combined volume of all boxes is: " + (box1.Volume() + box2.Volume() + box3.Volume() + box4.Volume()));
		
		
		
		box1.setColor("yellow");
		box1.setHeight(15);
		
		
		System.out.println("\n" + box1.toString());
		System.out.println("\n" + box2.toString());
		System.out.println("\n" + box3.toString());
		System.out.println("\n" + box4.toString());
	}

}
