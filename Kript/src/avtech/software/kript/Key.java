package avtech.software.kript;

import java.math.BigInteger;

public class Key {

	private String keyInts = "";
	private String key = "";
	private BigInteger numberKey;
	private static final long prime = 982451653;

	public Key() {
		generate();
	}

	public Key(Key privateKey) {
		generate(privateKey);
	}

	private void generate() {
		for (int i = 1; i < 20; i++) {
			long addingValue = i * 3 * prime;

			keyInts = keyInts + addingValue;
			key = key + Integer.toHexString((int) addingValue);
		}
		numberKey = new BigInteger(keyInts);
	}

	private void generate(Key pk) {

	}

	public void print() {
		System.out.println("Int: " + numberKey.toString());
		System.out.println("Key: " + key);
	}

	public String getKey() {
		return key;
	}
}
