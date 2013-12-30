package avtech.software.kript;

import java.math.BigInteger;
import java.util.Random;

public class Key {

	private static final long primeNumber = 32416190071L;
	private String stringKey = "";
	private String hexKey = "";
	private long longKey;
	private long multiplier;
	private BigInteger bigIntKey;
	private static Random random = new Random();

	public Key() {
		generate();
	}

	public Key(Key privateKey) {
		generate(privateKey);
	}

	private void generate() {

	}

	private void generate(Key pubKey) {

	}

	public void print() {
		System.out.println("String: " + stringKey);
		System.out.println("Hex: " + hexKey);
		System.out.println("Long: " + longKey);
	}
}
