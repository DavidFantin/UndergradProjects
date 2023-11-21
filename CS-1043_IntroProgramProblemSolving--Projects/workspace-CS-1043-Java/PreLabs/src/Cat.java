
public class Cat {

	private double age;
	private int wisker_count;
	private String breed;
	
	
	public Cat(double A, int WC, String B)
	{
		age = A;
		wisker_count = WC;
		breed = B;
	}
	
	public double getFC()
	{	return age;	}
	
	public int getWC()
	{	return wisker_count;	}
	
	public String getB()
	{	return breed;		}
	
	
	public void setFC(double A)
	{	age = A;		}
	
	public void setEC(int WC)
	{	wisker_count = WC;		}
	
	public void setB(String B)
	{	breed = B;	}
	
	
	public String toString()
	{
		return "Age: " + age + "\nWisker Count: " + wisker_count + "\nBreed: " + breed;
	}
}
