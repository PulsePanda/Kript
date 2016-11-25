/*
 *  Kript is a Java based network encryption library
 * 
 *  Copyright (C) 2016  Austin VanAlstyne

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Kript;

import java.math.BigInteger;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private Prime prime1; // First Prime
	private Prime prime2; // Second Prime
	private long primeQuotient; // Both primes multiplied together. Otherwise
								// known as 'n'
	private long totient; // (prime1 - 1)(prime2 - 1) formally 'eN'
	private long publicKeyExponent; // released as the public key exponent
	private long privateKeyExponent = 1; // kept as the private key exponent

	private Key privateKey;
	private Key publicKey;
	private Key remotePublicKey;

	/**
	 * Default constructor. Calling this constructor, Kript will generate it's
	 * own prime numbers for key creation.
	 */
	public Kript() {
		prime1 = new Prime();
		prime2 = new Prime();

		primeQuotient = prime1.getPrime() * prime2.getPrime();
		totient = (prime1.getPrime() - 1) * (prime2.getPrime() - 1);
		generatePublicKeyPrime();
		generatePrivateKeyPrime();
		generateKeypair();
	}

	/**
	 * LESS SECURE
	 * 
	 * Constructor. Allows you to assign your own prime numbers to Kript for key
	 * generation.
	 * 
	 * @param p1
	 *            first prime number you want used.
	 * @param p2
	 *            second prime number you want used.
	 */
	public Kript(long p1, long p2) {
		prime1 = new Prime(p1);
		prime2 = new Prime(p2);

		primeQuotient = prime1.getPrime() * prime2.getPrime();
		totient = (prime1.getPrime() - 1) * (prime2.getPrime() - 1);
		generatePublicKeyPrime();
		generatePrivateKeyPrime();
		generateKeypair();
	}

	/**
	 * Generates the public key's prime number. Creates new prime numbers until
	 * it is not a factor of the totient. When it is, the publicKeyPrime is
	 * assigned to it.
	 */
	private void generatePublicKeyPrime() {
		Prime temp = new Prime();
		boolean success = false;
		while (!success) {
			if (totient % temp.getPrime() == 0)
				temp = new Prime();
			else
				success = true;
		}

		publicKeyExponent = temp.getPrime();
	}

	/**
	 * Assigns private key prime to the modular multiplicative inverse of the
	 * public key prime.
	 */
	private void generatePrivateKeyPrime() {
		privateKeyExponent = BigInteger.valueOf(publicKeyExponent).modInverse(BigInteger.valueOf(totient)).longValue();
	}

	/**
	 * Generates the keypair to be used, assigning them to the appropriate
	 * variables.
	 */
	private void generateKeypair() {
		privateKey = new Key(primeQuotient, privateKeyExponent);
		publicKey = new Key(primeQuotient, publicKeyExponent);
	}

	/**
	 * Encrypt a byte, returns the long version of it.
	 * 
	 * @param bytes
	 * @return
	 */
	public long encrypt(byte bytes) {
		long msg;
		long n = remotePublicKey.getPrimeQuotient();
		long e = remotePublicKey.getKeyExponent();

		long value = bytes;

		for (int z = 0; z < e - 1; z++) {
			value = (value * bytes) % n;
		}
		msg = value;

		return msg;
	}

	/**
	 * Decrypt an encrypted byte, return the long version of the decryption.
	 * 
	 * @param s
	 *            encrypted byte message
	 * @return
	 */
	public long decrypt(long s) {
		long n = privateKey.getPrimeQuotient();
		long d = privateKey.getKeyExponent();
		BigInteger encryptedMessage = new BigInteger(Long.toString(s));
		BigInteger message = encryptedMessage.modPow(new BigInteger(Long.toString(d)),
				new BigInteger(Long.toString(n)));

		return message.longValue();
	}

	/**
	 * Set the public key of your connection.
	 * 
	 * @param k
	 */
	public void setRemotePublicKey(Key k) {
		remotePublicKey = k;
	}

	/**
	 * Get the public key.
	 * 
	 * @return PublicKey
	 */
	public Key getPublicKey() {
		return publicKey;
	}
}
