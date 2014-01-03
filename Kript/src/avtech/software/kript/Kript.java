package avtech.software.kript;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private PrivateKey privateKey1, privateKey2;
	private PublicKey publicKey;
	private String remotePublicKey;

	public Kript(String rpk) {
		generateKeys();
		setRemotePublicKey(publicKey.getKeyString());
	}

	private void generateKeys() {
		privateKey1 = new PrivateKey();
		privateKey2 = new PrivateKey();
		publicKey = new PublicKey(privateKey1, privateKey2);
	}

	public long[] encrypt(byte[] bytes) {
		long[] msg = new long[bytes.length];
		long rpkLong = Long.parseLong(remotePublicKey);

		// convert message into an array of bytes and encodes them
		for (int i = 0; i < bytes.length; i++) {
			msg[i] = (37 * bytes[i]) % rpkLong;
		}

		return msg;
	}

	public String decrypt(String s) {

		return null;
	}

	public void setRemotePublicKey(String k) {
		remotePublicKey = k;
	}

	public static void main(String[] args) {
		new Kript("1");
	}

	// method to copy a file into byte[] taken from the original server software
	public static byte[] copyFile(String dir) {
		File f = new File(dir);
		byte[] mybytearray = null;

		try {
			mybytearray = new byte[(int) f.length()];
			FileInputStream fis;
			fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mybytearray;
	}
}
