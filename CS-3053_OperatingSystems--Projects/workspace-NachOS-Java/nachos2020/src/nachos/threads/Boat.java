package nachos.threads;

import nachos.ag.BoatGrader;
import nachos.machine.*;

public class Boat {
	static BoatGrader bg;

	@SuppressWarnings("unused")
	public static void selfTest() {
		BoatGrader b = new BoatGrader();

		System.out.println("\n ***Boat Test Begins***");
		begin(10, 10, b);
		System.out.println(" ***Boat Test Ends***");

	}

	public static void begin(int adults, int children, BoatGrader b) {
		Lib.assertTrue(children >= 2 || adults + children == 1); // Ensures that there is no scenario that the code
																	// cannot compute
		// Store the externally generated autograder in a class
		// variable to be accessible by children.
		bg = b;

		// Instantiate global variables here
		adultsLeft = adults;
		childrenLeft = children;
		ChildrenOnBoat = 0; // the initial state of the boat is empty
		boatIsOnOahu = true;
		Lock = new Lock();
		ChildrenOnOahu = new Condition2(Lock);
		ChildrenOnMolokai = new Condition2(Lock);
		AdultsOnOahu = new Condition2(Lock);
		AdultsOnMolokai = new Condition2(Lock);
		end = new Condition2(Lock);

		// Create threads here. See section 3.4 of the Nachos for Java
		// Walkthrough linked from the projects page.

		for (int i = 0; i < children; i++) {
			Runnable r = new Runnable() {
				public void run() {
					ChildItinerary();
				}
			};
			KThread t = new KThread(r);
			t.setName("Child" + i);
			t.fork();

		}

		for (int i = 0; i < adults; i++) {
			Runnable r = new Runnable() {
				public void run() {
					AdultItinerary();
				}
			};
			KThread t = new KThread(r);
			t.setName("Adult" + i);
			t.fork();
		}

		Lock.acquire();
		end.sleep(); // A condition variable to signal the program to terminate
	}

	static void AdultItinerary() {
		/*
		 * This is where you should put your solutions. Make calls to the BoatGrader to
		 * show that it is synchronized. For example: bg.AdultRowToMolokai(); indicates
		 * that an adult has rowed the boat across to Molokai
		 */
		Lock.acquire();
		while (adultsLeft != 0) {
			// "childrenLeft <= 1" ensures that an adult can travel, since there is at least
			// 1 child on Molokai
			if (childrenLeft < 2 && boatIsOnOahu) {
				adultsLeft--;
				boatIsOnOahu = false;
				bg.AdultRowToMolokai();
				/////// FINISH_CONDITION \\\\\\\
				if (adultsLeft == 0 && childrenLeft == 0) {
					end.wake();
				}
				ChildrenOnMolokai.wakeAll(); // Getting ready to send a child back to Oahu
				AdultsOnMolokai.sleep();
			} else {
				AdultsOnOahu.sleep(); // Must sleep the threads on the Island that the boat is not
			}
		}

	}

	static void ChildItinerary() {
		Lock.acquire();
		boolean TMPboatIsOnOahu = true; // Secondary boolean to show where the boat is
		while (childrenLeft != 0 || adultsLeft != 0) // Should iterate until "end.wake()" is called
			// This deals with the children trying to get to Molokai
			if (TMPboatIsOnOahu == true) {
				// Checks if there is
				if (boatIsOnOahu && ChildrenOnBoat == 0) {
					TMPboatIsOnOahu = false;
					childrenLeft--;
					bg.ChildRowToMolokai();
					ChildrenOnBoat++; // There is now a child on the boat
					if (childrenLeft != 0) {
						ChildrenOnOahu.wakeAll();
					} 
					else {
						boatIsOnOahu = false;
						/////// FINISH_CONDITION \\\\\\\
						if (adultsLeft == 0 && childrenLeft == 0) {
							end.wake();
						}
						ChildrenOnMolokai.wakeAll(); // Together these lines help keep the adults
						ChildrenOnMolokai.sleep(); // and children synchronized
					}

				} else if (boatIsOnOahu && ChildrenOnBoat == 1) {
					TMPboatIsOnOahu = false;
					childrenLeft--;
					bg.ChildRideToMolokai();
					boatIsOnOahu = false;
					ChildrenOnBoat--;
					/////// FINISH_CONDITION \\\\\\\
					if (adultsLeft == 0 && childrenLeft == 0) {
						end.wake();
					}
					ChildrenOnMolokai.wakeAll(); // Together these lines help keep the adults
					ChildrenOnMolokai.sleep(); // and children synchronized
				} else {
					ChildrenOnOahu.sleep(); // Sleep the children left on the Island the boat just left (since now we
											// are dealing with Molokai children if it reaches here)
				}
			}
			// This deals with the children who must return to Oahu
			else {
				// Boat is on Molokai
				if (boatIsOnOahu == false) {
					bg.ChildRowToOahu();
					childrenLeft++;
					boatIsOnOahu = true;
					TMPboatIsOnOahu = true;
					AdultsOnOahu.wakeAll(); // Gets ready for an adult to row to Molokai
					ChildrenOnOahu.wakeAll(); // Together these lines help keep the adults
					ChildrenOnOahu.sleep(); // and children synchronized
				}
				// Boat is on Oahu
				else {
					ChildrenOnMolokai.sleep(); // Must sleep the threads on the Island that the boat is not
				}
			}
	}

	static void SampleItinerary() {
		System.out.println("\n ***Everyone piles on the boat and goes to Molokai***");
		bg.AdultRowToMolokai();
		bg.ChildRideToMolokai();
		bg.AdultRideToMolokai();
		bg.ChildRideToMolokai();
	}

	static int adultsLeft; // The # of adults left on Oahu
	static int childrenLeft; // The # of children left on Oahu
	static int ChildrenOnBoat; // Can only be: 0 or 1
	static boolean boatIsOnOahu; // Shows where the boat is
	static Condition2 ChildrenOnOahu;
	static Condition2 ChildrenOnMolokai;
	static Condition2 AdultsOnOahu;
	static Condition2 AdultsOnMolokai;
	static Condition2 end; // The end condition
	static Lock Lock;
}