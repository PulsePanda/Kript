package kript;

import java.io.Serializable;

public class PublicKey extends Key implements Serializable {

	private long n;
	private long e;

	public PublicKey(long n, long e) {
		this.n = n;
		this.e = e;
	}

	public long getN() {
		return n;
	}

	public long getE() {
		return e;
	}

	public void print() {
		System.out.println("publicKey: n: " + n + " e: " + e);
	}
}
