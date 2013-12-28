package avtech.software.kript;

public class Kript {

	private Key publicKey, privateKey;

	public Kript() {
		generateKeys();
	}

	private void generateKeys() {
		privateKey = new Key();
		publicKey = new Key(privateKey);

		String binary = privateKey.toBinaryString(privateKey.getKey(), false);
		privateKey.print();
		System.out.println(binary);

		privateKey.stringFromBinary(binary, false);
		
	}

	public static void main(String[] args) {
		new Kript();
	}
}
