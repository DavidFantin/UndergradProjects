//Name: David Fantin
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 1/30/19
//Assignment: Lab 04

import java.io.*;
import java.util.*;

public class Lab04
{
	public static void main(String[] args) 
	{
		boolean tryAgain;
		Scanner tokens = null;
		
		do{		
			Scanner console = new Scanner(System.in);
			System.out.printf("Enter file name to read: ");
			String inputFile = console.next();
			ArrayList<Double> items = new ArrayList<Double>();
			tryAgain = false;
		
			try{
				
				File fileR = new File(inputFile);
				tokens = new Scanner(fileR);
				
					while(tokens.hasNextDouble())
					{
						items.add(tokens.nextDouble());
					}
					System.out.println("The items contained within the file are as follows: ");
					System.out.println(items);
					Lab04 q = new Lab04();
					MinMaxObject List = q.new MinMaxObject(items);
					System.out.println("\nThe Minimum is: " + List.min + "\nThe Minimum Position is: " + List.minpos + "\nThe Maximum is: " + List.max + "\nThe Maximum Position is: " + List.maxpos);
			}
			catch(FileNotFoundException er)
			{
				System.out.printf("Error opening file %s, %s\n", inputFile, er);
				tryAgain = true;
			}
			
		}
		while(tryAgain);
	}
/***************************************************************************************************************/
	public class MinMaxObject
	{
		private double min;
		private double max;
		private int maxpos;
		private int minpos;
		private int maxbou;
		private int minbou;
		
		public MinMaxObject(ArrayList<Double> L)
		{
			min = FindMin(L, 0, L.size() - 1, L.get(L.size()-1));
			max = FindMax(L, 0, L.size() - 1, L.get(L.size()-1));
			maxpos = findMaxPos(L, 0, L.size() - 1, this);
			minpos = findMinPos(L, 0, L.size() - 1, this);
		}
		public MinMaxObject(ArrayList<Double> L, int minbound, int maxbound)
		{
			min = FindMin(L, (minbound - 1), maxbound - 1, L.get(L.size()-1));
			max = FindMin(L, (minbound - 1), maxbound - 1, L.get(L.size()-1));
			maxpos = findMaxPos(L,(minbound - 1), maxbound - 1, this);
			minpos = findMinPos(L,(minbound - 1), maxbound - 1, this);
		}
		
		public double FindMin(ArrayList<Double> L, int minposition, int maxposition, double min)
		{
			if(maxposition == minposition)
			{
				min = min(min, L.get(minposition));
				return min;
			}
			else{
				min = FindMin(L, minposition, (maxposition-1), min(min, L.get(maxposition)));
				return min;
			}
		}
		
		public double FindMax(ArrayList<Double> L, int minposition, int maxposition, double max)
		{
			if(minposition == maxposition)
			{
				max = max(max, L.get(maxposition));
				return max;
			}
			else{
				max = FindMax(L, (minposition + 1), maxposition , max(max, L.get(minposition)));
				return max;
			}
		}
		
		public int findMaxPos(ArrayList<Double> L, int minposition, int maxposition, MinMaxObject item)
		{
			if(maxposition == minposition)
			{
				return maxposition + 1;
			}
			else{
				if(L.get(maxposition) > item.getMax()){
					return findMaxPos(L, (minposition + 1), maxposition, item);
				}
				else if(L.get(maxposition) < item.getMax()){
					return findMaxPos(L, minposition, (maxposition - 1), item);
				}
				else{
					return maxposition + 1;
				}
			}
		}
		
		public int findMinPos(ArrayList<Double> L, int minposition, int maxposition, MinMaxObject item)
		{
			if(minposition == maxposition)
			{
				return minposition + 1;
			}
			else{
				if(L.get(minposition) > item.getMin()){
					return findMinPos(L, (minposition+1) , maxposition , item);
				}
				else if(L.get(minposition) < item.getMin()){
					return findMinPos(L, minposition , (maxposition-1), item);
				}
				else{
					return minposition + 1;
				}
			}
		}
		public double min(double i, double j)
		{
			if(i < j)
			{
				return i;
			}else{
				return j;
			}
		}
		
		public double max(double i, double j)
		{
			if(i > j)
			{
				return i;
			}else{
				return j;
			}
		}
		
		public double getMin()
		{
			return min;
		}
		public double getMax()
		{
			return max;
		}
		public int getMinPos()
		{
			return minpos;
		}
		public int getMaxPos()
		{
			return maxpos;
		}
	}
}