package avtech.software.kript;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Random;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private Prime p = new Prime();
	private Prime q = new Prime();
	private long n;
	private long eN;
	private long e; // released as the public key exponent
	private long d = 1; // kept as the private key exponent

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
		Prime temp = new Prime();
		boolean success = false;
		while (!success) {
			if (eN % temp.getPrime() == 0)
				temp = new Prime();
			else
				success = true;
		}

		// while (!Prime.isCoprime(temp.getPrime(), eN)) {
		// temp = new Prime();
		// }

		e = temp.getPrime();
	}

	private void genD() {
		d = BigInteger.valueOf(e).modInverse(BigInteger.valueOf(eN))
				.longValue();
	}

	private void genKeys() {
		privateKey = new PrivateKey(n, d);
		publicKey = new PublicKey(n, e);
	}

	public long encrypt(byte bytes) {
		long msg;
		long n = remotePublicKey.getN();
		long e = remotePublicKey.getE();

		long value = bytes;

		for (int z = 0; z < e - 1; z++) {
			value = (value * bytes) % n;
		}
		msg = value;

		return msg;
	}

	public long decrypt(long s) {
		long msg;
		long n = privateKey.getN();
		long d = privateKey.getD();

		long value = s;
		for (int i = 0; i < d - 1; i++) {
			value = (value * s) % n;
		}

		msg = value;
		return msg;
	}

	public void setRemotePublicKey(PublicKey k) {
		remotePublicKey = k;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
