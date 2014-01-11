package avtech.software.kript;

public class PrivateKey extends Key {

	private long n;
	private long d;

	public PrivateKey(long n, long d) {
		this.n = n;
		this.d = d;
	}
}
