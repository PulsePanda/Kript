package avtech.software.kript;

public class Kript {

	private PrivateKey privateKey1, privateKey2;
	private PublicKey publicKey;
	private String remotePublicKey;

	public Kript(String rpk) {
		setRemotePublicKey(rpk);
		generateKeys();
	}

	private void generateKeys() {
		privateKey1 = new PrivateKey();
		privateKey2 = new PrivateKey();
		publicKey = new PublicKey(privateKey1, privateKey2);
	}

	public String encrypt(String s) {
		byte[] msgBytes = s.getBytes();
		String msg = "";
		long intMsg;

		// convert message into an array of bytes
		for (int i = 0; i < msgBytes.length; i++) {
			msg = msg + msgBytes[i];
		}

		// convert all values to long, then multiply them and re-convert them
		intMsg = Long.parseLong(msg);
		intMsg *= Long.parseLong(remotePublicKey);
		msg = Long.toString(intMsg);

		return msg;
	}

	public String decrypt(String s) {

		return null;
	}

	public void setRemotePublicKey(String k) {
		remotePublicKey = k;
	}

	public static void main(String[] args) {
		new Kript("1").encrypt("hello");

		/**
		 * test 2
		 */
		// BigInteger multiply = new BigInteger(privateKey.toBinaryString(
		// privateKey.getKey(), false), 2).multiply(new BigInteger(
		// "01100011", 2));
		// System.out.println(multiply.toString(2));

		/**
		 * multiplication example
		 */
		// System.out.println(new BigInteger(privateKey.toBinaryString(
		// privateKey.getKey(), false), 2).multiply(
		// new BigInteger("01100011")).toString(2));

	}
}
