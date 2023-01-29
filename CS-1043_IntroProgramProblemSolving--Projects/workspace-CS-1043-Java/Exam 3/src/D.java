public class D extends Tests
{   public D()  {}
	public void print(int n)
	{   
		if(n <= 1) super.print(n);
		else if (n % 2 == 0) print(n / 2);
		else print(3 * n + 1);
	}
}
