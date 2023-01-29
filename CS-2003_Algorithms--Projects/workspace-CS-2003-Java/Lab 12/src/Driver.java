import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * Driver class to test the 3 implementations of:
 *Linear Probing, Quadratic Probing and Double Hashing
 */
public class Driver {
	public static <E> void main(String args[]) {
		
		// Create array list to store KeyValue Pairs from file 
		ArrayList<KeyValuePair<E>> list = new ArrayList<KeyValuePair<E>>();
		try {
			// Read file and store values in list
			Scanner scanner = new Scanner(new File("country-capitals.csv"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] splitLine = line.split(",");
				list.add(new KeyValuePair(splitLine[0], splitLine[1]));
			}
			// TO COMPLETE : Choose/change load factor
			int loadFactor = 50;
			PrimeNumber primeNumber = new PrimeNumber();
			int tableSize = primeNumber.findNextPrime(list.size()*100/loadFactor);
			System.out.println(tableSize);
			
			// Create instances of the 3 hash classes
			LinearProbe<E> linHash = new LinearProbe(tableSize);
			QuadraticProbe<E> quadHash = new QuadraticProbe(tableSize);
			DoubleHash<E> doubleHash = new DoubleHash(tableSize);
			// Create placeholders for storing number of collisions and runtimes
			int numCollisionsLin = 0, numCollisionsQuad = 0, numCollisionsDouble = 0;
			long runTimeLin = 0, runTimeQuad = 0, runTimeDouble = 0;
			
			// TEST for addition of elements
			// Iterate over list and add elements to respective hash objects
			for (KeyValuePair<E> kv : list) {

				// Linear probing
				long startTime = System.nanoTime();
				int linCollision = linHash.add(kv);
				runTimeLin += System.nanoTime() - startTime;

				// Quadratic probing
				startTime = System.nanoTime();
				int quadCollision = quadHash.add(kv);
				runTimeQuad += System.nanoTime() - startTime;

				// Double hashing
				startTime = System.nanoTime();
				int doubleCollision = doubleHash.add(kv);
				runTimeDouble += System.nanoTime() - startTime;
				
				numCollisionsLin += linCollision;
				numCollisionsQuad += quadCollision;
				numCollisionsDouble += doubleCollision;
			}
			
			System.out.println("Adding time and collision analysis:");
			System.out.println("Linear Probing had " + numCollisionsLin + " collisions with runtime " + runTimeLin + " ns");
			System.out.println("Quadratic Probing had " + numCollisionsQuad + " collisions with runtime " + runTimeQuad + " ns");
			System.out.println("Double Probing had " + numCollisionsDouble + " collisions with runtime " + runTimeDouble + " ns");

			// Create arraylist and store random elements to be retrieved
			ArrayList<KeyValuePair<E>> randomList = new ArrayList<KeyValuePair<E>>();
			Random randomNumGenerator = new Random();
			for (int i = 0; i < 200; i++) {
				randomList.add(list.get(randomNumGenerator.nextInt(list.size())));
			}
			// TEST for retrieval of elements
			runTimeLin = 0; runTimeQuad = 0; runTimeDouble = 0;
			for (KeyValuePair<E> kv : randomList) {

				// Linear probing
				long startTime = System.nanoTime();
				KeyValuePair<E> item = linHash.retrieve(kv.getKey());
				runTimeLin += System.nanoTime() - startTime;

				// Quadratic probing
				startTime = System.nanoTime();
				item = quadHash.retrieve(kv.getKey());
				runTimeQuad += System.nanoTime() - startTime;

				// Double hashing
				startTime = System.nanoTime();
				item = doubleHash.retrieve(kv.getKey());
				runTimeDouble += System.nanoTime() - startTime;

			}
			System.out.println("\nRetrieval time analysis:");
			System.out.println("Linear Probing retrieval time: " + runTimeLin + " ns");
			System.out.println("Quadratic Probing retrieval time: " + runTimeQuad + " ns");
			System.out.println("Double Probing retrieval time: " + runTimeDouble + " ns");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
