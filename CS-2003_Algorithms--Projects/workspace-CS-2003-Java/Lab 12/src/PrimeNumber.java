
public class PrimeNumber {
	public boolean isPrime(int n) {
		for(int i = 2; i*i <=n ;i++) {
			if (n%i == 0) {
				return false;
			}
		}
		return true;
	}
	public int findNextPrime(int x) {
		if (x%2 == 0)
			x++;
		while(!isPrime(x)) {
			x+=2;
		}
		return x;
	}
}
