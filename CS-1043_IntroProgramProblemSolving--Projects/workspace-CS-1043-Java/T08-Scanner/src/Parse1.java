import java.util.Scanner;

public class Parse 1
{
public
static
void

main( String[] args )
{   
Scanner fields = 
null
;
Scanner console = 
new
Scanner(System.
in
);
System.
out
.printf("Please enter a line of text to parse: 
\
n");
String record = console.nextLine();  the next full line!
while
( record.length() > 0 )  if not zero length, 
parse it.
{   
 The default 
delimiter 
is at least one white space character:  
\
\
s+
fields = 
new
Scanner( record );  .useDelimiter("
\
\
s+");
while
( fields.hasNext()  )  parse a record.
{
String token = fields.next();
System.
out
.printf( token + "
\
n" );
}
if
( fields != 
null
) fields.close();
System.
out
.printf( "Please enter a line of text to parse: 
\
n" );
record = console.nextLine();
} // end while
System.
out
.println("Exiting program");
if
( 
console != 
null
) console.close();
} // end

} // end class Parse
