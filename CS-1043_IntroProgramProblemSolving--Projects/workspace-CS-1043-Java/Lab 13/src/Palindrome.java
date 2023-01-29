/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/15/18
Assignment: Lab 13 Palindromes
Description: This program uses recursion to determine if a string is a palindrome or not.
*/

import javax.swing.JOptionPane;

public class Palindrome 
{

	public static void main(String[] args) 
	{
		while (true) 
		{
			String msg = "Enter a palindrome to test: ";
			System.out.println(msg);
			String input = JOptionPane.showInputDialog(msg);

			if (isaPalindrome(input))
				msg = String.format("The input string [ %s ] is a palindrome: %s \n", input, reverseStr(input));
			else // the input string is NOT a palindrome.
				msg = String.format("The input string [ %s ] is not a palindrome.\n", input);

			System.out.printf(msg, input);

			JOptionPane.showMessageDialog(null, msg);

			backw1(input);
			System.out.print(" (backw1)");
			System.out.println();

			backw2(input);
			System.out.print(" (backw2)");
			System.out.println();

		}
	}

	public static boolean isaPalindrome(String str) 
	{
		char c;
		String compress = "";
		boolean test = false;

		for (int i = 0; i < str.length(); i++) 
		{
			c = str.charAt(i);
			if (c >= 'A' && c <= 'Z')
				compress += ((char) (c + 32));
			else if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9')
				compress += (char) c;

		}
		System.out.println("Test the string: " + compress);
		String Rstr = reverseStr(compress);
		if (compress.equals(Rstr))
			test = true;
		else
			test = false;

		return test;
	}

	public static void backw1(String str) 
	{
		int strL = str.length();

		if (strL > 1)
			backw1(str.substring(1, strL));
		System.out.print(str.charAt(0));
	}

	public static void backw2(String str)
	{
		int strL = str.length();

		System.out.print(str.charAt(strL - 1));

		if (strL > 1)
			backw2(str.substring(0, strL - 1));
	}

	public static String reverseStr(String inStr) 
	{
		int sLen = inStr.length();
		if (sLen > 1)
			return reverseStr(inStr.substring(1, sLen)) + inStr.charAt(0);
		else
			return inStr;
	}

}