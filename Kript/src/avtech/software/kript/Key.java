package avtech.software.kript;

import java.util.ArrayList;

public class Key {

	private String key = "";
	private static final long prime = 982451653;

	public Key() {
		generate();
	}

	public Key(Key privateKey) {
		generate(privateKey);
	}

	private void generate() {
		for (int i = 0; i < 20; i++) {
			key = key + Integer.toHexString((int) (System.nanoTime() * prime));
		}
	}

	private void generate(Key privateKey) {

	}

	public String toBinaryString(String s, boolean withSpaces) {
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}

			if (withSpaces)
				binary.append(' ');
		}

		return binary.toString();

	}

	public String stringFromBinary(String s, boolean stringHasSpaces) {
		if (!stringHasSpaces) {
			StringBuilder sb = new StringBuilder();
			sb.append(s);
			for (int i = 0; i < s.length(); i++) {
				if (i % 9 == 0 && i != 0) {
					sb.insert(i - 1, " ");
				}
			}

			System.out.println(sb.toString());
			System.exit(0);
		}

		String[] ss = s.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ss.length; i++) {
			sb.append((char) Integer.parseInt(ss[i], 2));
		}

		return sb.toString();
	}

	public void print() {
		System.out.println("KEY: " + key);
	}

	public String getKey() {
		return key;
	}
}
