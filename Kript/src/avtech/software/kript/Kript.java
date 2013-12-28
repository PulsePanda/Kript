package avtech.software.kript;

public class Kript {

	private Key publicKey, privateKey;

	public Kript() {
		generateKeys();
	}

	private void generateKeys() {
		privateKey = new Key();
		publicKey = new Key(privateKey);
	}

	public static void main(String[] args) {
		new Kript();
	}
}
