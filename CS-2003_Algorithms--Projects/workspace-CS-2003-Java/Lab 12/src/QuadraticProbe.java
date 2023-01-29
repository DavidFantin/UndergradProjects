
/**
 * Quadratic probing class
 */
public class QuadraticProbe<E> {
	// status contain 1 if an item is present, 0 if not
	private int[] status;
	// stores the elements of type KeyValuePair<E>
	private KeyValuePair<E>[] table;
	
	// constructor
	public QuadraticProbe(int lengthOfTable) {
		status = new int[lengthOfTable];
		table = new KeyValuePair[lengthOfTable];		
	}
	
	public int[] getStatus() {
		return status;
	}
	
	public KeyValuePair<E>[] getTable() {
		return table;
	}
	
	/** 
	 * hashing function
	 * @param str
	 * @return int: hashed value of string str
	 */
	public int hash(String str) {
		return Math.abs(str.hashCode())%status.length;
	}

	/**
	 * TO BE COMPLETED
	 * method to add an item of type KeyValuePair<E> 
	 * @param kv of type KeyValuePair<E>
	 * @return int: number of collisions due to addition of kv
	 */
	public int add(KeyValuePair<E> kv) {
	}

	/**
	 * TO BE COMPLETED
	 * method to retrieve an item from the hashtable 
	 * given the key to look for
	 * @param String:  key
	 * @return   KeyValuePair<E>: item 
	 */
	public KeyValuePair<E> retrieve(String key){
	}
}
