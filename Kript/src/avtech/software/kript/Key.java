package avtech.software.kript;

import java.util.Random;

public class Key {

	protected long key;
	protected boolean prime = false;
	protected static Random random = new Random();

	public Key() {

	}

	public long getKey() {
		return key;
	}

	public void print() {
		System.out.println("key: " + key);
	}
}
