/*
* Kript
* Created by Austin VanAlstyne
* Open Source with Related GitHub Repo
*
* Copyright© 2015 Austin VanAlstyne
*/

/*
*This file is part of Kript.
*
*Kript is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*Kript is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with Kript.  If not, see <http://www.gnu.org/licenses/>.
 */

package kript;

import java.math.BigInteger;

import javax.swing.JOptionPane;

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
		// long msg;
		long n = privateKey.getN();
		long d = privateKey.getD();
		//
		// long value = s;
		//
		// for (int i = 0; i < d - 1; i++) {
		// value = (value * s) % n;
		// }
		// JOptionPane.showMessageDialog(null, value);
		//
		// msg = value;
		// return msg;
		BigInteger encryptedMessage = new BigInteger(Long.toString(s));
		BigInteger message = encryptedMessage.modPow(
				new BigInteger(Long.toString(d)),
				new BigInteger(Long.toString(n)));

		return message.longValue();
	}

	public void setRemotePublicKey(PublicKey k) {
		remotePublicKey = k;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
