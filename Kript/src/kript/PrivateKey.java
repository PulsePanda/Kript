package kript;

public class PrivateKey extends Key {

	private long n;
	private long d;

	public PrivateKey(long n, long d) {
		this.n = n;
		this.d = d;
	}

	public long getN() {
		return n;
	}

	public long getD() {
		return d;
	}

	public void print() {
		System.out.println("privateKey: n: " + n + " d: " + d);
	}
}
