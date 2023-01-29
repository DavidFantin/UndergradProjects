//Name: David Fantin
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 2/13/19
//Assignment: Lab 06
/**
* Lab6: Complete the recursive method isaPalindrome
 *
 */

import java.util.Scanner;

public class Lab6{


    public static void main(String[] args){
	/******************************************************** Palindrome */
	if (!isaPalindrome("satanoscillatemymetallicsonatas"))
	    System.out.println("You have a bug in your code!");
	else
	    System.out.println("check for palidrome ok");
			    
	Scanner console = new Scanner(System.in);
	System.out.print("Input a string\n>");
	String line = console.nextLine();
	while (!line.equals("")){
	    if (isaPalindrome(line))
		System.out.println(line+ " is a palindrome");
	    else
		System.out.println(line+ " is *not* a palindrome");
	    System.out.print("Input a string\n >");
	    line = console.nextLine();
	}
    }

    /** method to determine whether the specified string is a palindrome
     * @param expr string 
     * @return true if the specified string is a palindrome, otherwise false.
     */
    public static boolean isaPalindrome(String expr){
    	int L = expr.length()-1;
    	int J = L/2;
    	int I = 0;
    	if(I == J || I == L)
    	{
    		return true;
    	}
    		if(expr.charAt(I) == expr.charAt(L))
    		{
    			return isaPalindrome(expr.substring(1, expr.length()-1));
    		}else{
    			return false;
    		}
    }		
}



































//https://gist.github.com/maany/6a7cec38585a38f20f33872abeff533b
