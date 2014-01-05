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
	private long modulus;
	private long etfMod;
	private long e;

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String remotePublicKey;

	private static Random random = new Random();

	public Kript(String rpk) {
		modulus = p.getPrime() * q.getPrime();
		etfMod = (p.getPrime() - 1) * (q.getPrime() - 1);
		genE();
		genKeys();
		setRPK(rpk);
	}

	private void genE() {
		long temp = etfMod / 2;

		while (!Prime.isCoprime(temp, etfMod)) {
			temp--;
		}

		e = temp;
	}

	private void genKeys() {
		privateKey = new PrivateKey();
		publicKey = new PublicKey();
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

	public void setRPK(String k) {
		remotePublicKey = k;
	}

	public static void main(String[] args) {
		// new Kript("1");
		System.out.println(17 % 3120);
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
