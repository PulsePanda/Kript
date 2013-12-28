package avtech.software.kript;

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

		String[] ss = null;

		if (!stringHasSpaces) {
			ss = addSpacesToBinary(s).split(" ");
		} else {
			ss = s.split(" ");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ss.length; i++) {
			sb.append((char) Integer.parseInt(ss[i], 2));
		}

		return sb.toString();
	}

	private String addSpacesToBinary(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		int length = s.length();
		for (int i = 0; i < length; i++) {
			if (i % 9 == 0 && i != 0) {
				sb.insert(i - 1, " ");
				length = sb.length();
			}
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
