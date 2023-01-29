import java.util.Scanner;

public class Parse {
public static void main( String[] args)
{   
	Scanner fields = null;
	Scanner console = new Scanner(System.in);
// **************** Start Various options --Pick one ****
//	System.out.printf("Please enter a line of text to parse: ");
//   String record = console.nextLine();
//	String record = "The quick brown fox jumps over the lazy dog";
	String record = "John Smith: 100 MainSt. : Anywhere, OK:74117";
//  String record = "George Washington was the first President.";
//  String record = "Campus motto: Bottoms up, Mac!"
//  String record = "The famous leaning tower is located in Pisa, Italy.";
// **************** Start Various options --Pick one ****
//	fields = new Scanner( record ).useDelimiter("\\s+");
//	fields = new Scanner( record ).useDelimiter( ": *");
//	fields = new Scanner( record ).useDelimiter( " +|: *|, *"); 
	
	while( fields.hasNext()  ) // parse a record.
	{
		String token = fields.next();
		System.out.printf( token + "\n");
		}
	if( fields!= null) 
		fields.close();
	System.out.println("Exiting program");
	if( console!= null) 
		console.close();
}
	// end main
}