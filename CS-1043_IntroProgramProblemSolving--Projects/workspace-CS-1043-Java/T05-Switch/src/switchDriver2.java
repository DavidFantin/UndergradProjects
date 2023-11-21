/* 
Name: David Fantin
Lab Section: 1043-02
Lecture Section: 1040-L1
Date: 9/17/18
Assignment: Learn basics of switches
Description: Basic switches
*/

import java.util.Scanner;

public class switchDriver2 {
	public static void main(String[] args) {
 
		Scanner console = new Scanner(System.in);
		System.out.print("Enter the room number: ");
		int roomNum = console.nextInt();

		int nseats = seatsNroom(roomNum);

		System.out.println("Seats in room " + roomNum + ": " + nseats);

		console.close();

	}

	public static int seatsNroom(int roomNum)
	{
		int nseats;
		
		switch(roomNum)
		{
		  case 110:
		  case 101:
		  case 116: nseats = 50;
					  break;
		  case 112:
		  case 114: nseats = 35;
					  break;
		  default:  nseats = 40;
	}

		  return nseats;
}
	public static int seatsNroom1(int roomNum)
	{
		int nseats;
		int level = roomNum/100;
		switch(level)
		{
		case 2: nseats = 65; break;
		case 3: nseats = 80; break;
		case 4: if(roomNum == 425) nseats = 350;
								else nseats = 90;
				break;
		case 1:
			switch(roomNum)
			{
			  case 110:
			  case 101:
			  case 116: nseats = 50; break;
			  case 112:
			  case 114: nseats = 35; break;
			  default:  nseats = 40;
		}
			  break;
		default: nseats = 0;
		
		}
		return nseats;
	}
}

