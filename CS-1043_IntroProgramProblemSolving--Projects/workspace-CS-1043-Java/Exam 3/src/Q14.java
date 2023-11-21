
public class Q14 {
	public int Help(int x, int y) 
	{
		x = 6;
		y = 14;
		int n;
		
		if (x > y){
			n = x + y;
			System.out.print(n);
			return (n);
		}
			
		if (x == y){
			n = Help(x + 2, y - 1) + Help(x, y - 2);
			System.out.print(n);
			return (n);
		}
			
		else{
			n = Help(x + 1, y - 2) + Help(x + 2, y - 3);
			System.out.print(n);
			return (n);
		}
	}// end Help
}
