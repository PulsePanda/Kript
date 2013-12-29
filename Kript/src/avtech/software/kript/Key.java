package avtech.software.kript;

import java.math.BigInteger;

public class Key {

	private String intStringKey = "";
	private String hexKey = "";
	private BigInteger bigIntKey;
	private static final long primeNumber = 982451653;

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
		System.out.println("Int: " + bigIntKey.toString());
		System.out.println("Key: " + hexKey);
	}

	public String getKey() {
		return hexKey;
	}
}
