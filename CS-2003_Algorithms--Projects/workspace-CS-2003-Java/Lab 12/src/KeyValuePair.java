
public class KeyValuePair<E> {
	private String key; 
	private E value;
	public KeyValuePair(String k, E val){
		key  = k;
		value = val; 
	}
	public String getKey() {
		return key;
	}
	
	public E getValue() {
		return value;
	}
	
}
