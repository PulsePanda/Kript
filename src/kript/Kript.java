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
	private BigInteger primeQuotient; // Both primes multiplied together.
										// Otherwise
	// known as 'n'
	private BigInteger totient; // (prime1 - 1)(prime2 - 1) formally 'eN'
	private BigInteger publicKeyExponent; // released as the public key exponent
	private BigInteger privateKeyExponent = new BigInteger("1"); // kept as the
																	// private
																	// key
																	// exponent

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

		primeQuotient = prime1.getPrime().multiply(prime2.getPrime());
		totient = (prime1.getPrime().subtract(new BigInteger("1")))
				.multiply((prime2.getPrime().subtract(new BigInteger("1"))));
		generatePublicKeyPrime();
		generatePrivateKeyPrime();
		generateKeypair();
	}

	public static void main(String[] args) {
		byte[] temp = "hello".getBytes();
		Kript k = new Kript();
		Key p = new Kript().getPublicKey();
		k.setRemotePublicKey(p);
		k.encrypt(temp[0]);
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
			if (totient.mod(temp.getPrime()).equals(0))
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
		privateKeyExponent = publicKeyExponent.modInverse(totient);
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
	 * @param bytes[]
	 *            Byte array to encrypt
	 * @return
	 */
	public BigInteger encrypt(byte bytes) {
		System.out.println("encrypting");
		BigInteger n = remotePublicKey.getPrimeQuotient();
		BigInteger e = remotePublicKey.getKeyExponent();
		BigInteger msg = BigInteger.valueOf(Long.parseLong(String.valueOf(bytes))).modPow(e, n);

//		BigInteger value = new BigInteger(Byte.toString(bytes));

//		BigInteger z = new BigInteger("0");
//
//		System.out.println("before for");
//		BigInteger whileHelper = e.subtract(new BigInteger("1"));
//		while (whileHelper.compareTo(z) == 1) {
//			value = (value.multiply(new BigInteger(Byte.toString(bytes)))).mod(n);
//			z = z.add(new BigInteger("1"));
//		}
//		System.out.println("after for");

//		msg = value;

		return msg;
	}

	/**
	 * Decrypt an encrypted byte, return the long version of the decryption.
	 * 
	 * @param s
	 *            encrypted byte message
	 * @return
	 */
	// public long decrypt(long s) {
	// long n = privateKey.getPrimeQuotient();
	// long d = privateKey.getKeyExponent();
	// BigInteger encryptedMessage = new BigInteger(Long.toString(s));
	// BigInteger message = encryptedMessage.modPow(new
	// BigInteger(Long.toString(d)),
	// new BigInteger(Long.toString(n)));
	//
	// return message.longValue();
	// }

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
