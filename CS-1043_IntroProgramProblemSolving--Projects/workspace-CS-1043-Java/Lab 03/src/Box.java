/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/6/18
Assignment: create boxes
Description: this code create boxes and spits out the properties of them
*/

public class Box {

			 // These are the declarations for the private instance fields:
			 private String color;
			 private double height;
			 private double width;
			 private double leingth; // Complete this declaration correctly

			 public String getColor()
			 {return color;}
			 
			 public void setColor( String C)
			 {color = C;}
			 
			 public double getHeight()
			 {return height;}
			 
			 public void setHeight(double H)
			 {height = H;}
			 
			 public double getWidth()
			 {return width;}
			 
			 public void setWidth(double W) 
			 {width = W;}
			 
			 public double getLeingth()
			 {return leingth;}
			 
			 public void setLeingth(double L)
			 {leingth = L;}
			 
			 public Box()
			 {
			   	 color = "white";
				 height = 12.0;
				 width = 12.0;
				 leingth = 12.0;
			 }
				 
			 public Box( double H, double W, double L, String C )
			 {
				 color = C;
				 width = H;
				 leingth = L;
				 height = H;
			 }

			public Box(Box bx)
				{
		     	  height = bx.getHeight();
				  width = bx.getWidth();
				  leingth = bx.getLeingth();
				  color = bx.getColor();
				}
		
			public double Volume()
			{
				double Volume = height * width * leingth;
				return Volume;	
			}
			
			public double Area()
			{
				double Area = height * width * 6;
				return Area;
			}

			public String toString()
			{
				String str = "The box color is " + this.getColor() + "\nThe height is: " + height + "\nThe width is: " + width + "\nthe leingth is: " + leingth + "\nThe area of one side is: " + Area() + "\nThe volume of the box is: " + Volume();
				return str;
			}

	}


