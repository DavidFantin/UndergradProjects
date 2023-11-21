
/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 11/29/18
Assignment: Lab 14
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ArrayListLab14 {
	public static void main(String[] args) {

		ArrayList<Integer> xobj = new ArrayList<Integer>();
		int AL = args.length;
		if (AL == 0) {
			System.out.println("$> java myJavaProgram [arg1, arg2, ...]");
			System.exit(1);
		}
		String filename = args[0];

		try {
			File file = new File(filename);
			Scanner tokens = new Scanner(file);

			while (tokens.hasNextInt()) {
				int i = 0;
				int value = tokens.nextInt();
				while (i < xobj.size() && value > xobj.get(i)) {
					i++;
				}
				xobj.add(i, value);
			}

			if (tokens.hasNextInt()) {
				xobj.add(0, tokens.nextInt());
			}

		}

		catch (IOException err) {
			System.err.printf("File error: %s\n", "err");
			System.exit(2);
		} // end IOException handler

		for (int i : xobj) {
			System.out.println(i);
		}

	} // end main

} // end ArrayListLab14 class
