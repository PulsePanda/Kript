package avtech.software.kript;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private Prime p = new Prime();
	private Prime q = new Prime();
	private long n;
	private long eN;
	private int e; // released as the public key exponent
	private int d = 1; // kept as the private key exponent

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private PublicKey remotePublicKey;

	private static Random random = new Random();

	public Kript() {
		n = p.getPrime() * q.getPrime();
		eN = (p.getPrime() - 1) * (q.getPrime() - 1);
		genE();
		genD();
		genKeys();
	}

	private void genE() {
		int temp = 7;

		while (!Prime.isCoprime(temp, eN)) {
			temp++;
		}

		e = temp;
	}

	private void genD() {
		// solve for d given d*e = 1 (mod eN)
		while ((d * e) % eN != 1) {
			d++;
		}
	}

	private void genKeys() {
		privateKey = new PrivateKey(n, d);
		publicKey = new PublicKey(n, e);
	}

	public long[] encrypt(byte[] bytes) {
		long[] msg = new long[bytes.length];
		long n = remotePublicKey.getN();
		long e = remotePublicKey.getE();

		for (int i = 0; i < msg.length; i++) {
			msg[i] = (bytes[i]);
		}

		return null;
	}

	public String decrypt(String s) {

		return null;
	}

	public void setRemotePublicKey(PublicKey k) {
		remotePublicKey = k;
	}

	public static void main(String[] args) {
		// new Kript();
		System.out.println((65 ^ 17) % 3233);
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
