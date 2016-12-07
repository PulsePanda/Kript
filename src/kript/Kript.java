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
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private Prime prime1;
	private Prime prime2;
	private BigInteger primeQuotient; // Both primes multiplied together
	private BigInteger totient; // (prime1 - 1)(prime2 - 1) formally 'eN'
	private BigInteger publicKeyExponent;
	private BigInteger privateKeyExponent = new BigInteger("1");

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
		System.out.println("KRIPT: Kript keys created and ready to be used.");
	}

	public Kript(boolean a) throws NoSuchAlgorithmException {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(512);
		byte[] key = gen.generateKeyPair().getPublic().getEncoded();
		StringBuffer retString = new StringBuffer();
        for (int i = 0; i < key.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (key[i] & 0x00FF)).substring(1));
        }
        System.out.println(retString);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		byte[] temp = "0xAC".getBytes();
		Kript k = new Kript();
		k.setRemotePublicKey(k.getPublicKey());
		BigInteger[] encryptedMessage = k.encrypt(temp);

		System.out.println(temp);
		System.out.println(String.valueOf(k.decrypt(encryptedMessage)));
//		new Kript(true);
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
	 * @return BigInteger[] of encrypted message
	 */
	public BigInteger[] encrypt(byte[] bytes) {
		BigInteger n = remotePublicKey.getPrimeQuotient();
		BigInteger e = remotePublicKey.getKeyExponent();
		BigInteger[] msg = new BigInteger[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			msg[i] = BigInteger.valueOf(Long.parseLong(String.valueOf(bytes[i]))).modPow(e, n);
		}

		return msg;
	}

	/**
	 * Decrypt an encrypted byte, return the long version of the decryption.
	 * 
	 * @param encryptedMessage
	 *            BigInteger[] encrypted byte message
	 * @return byte[] array containing message values
	 */
	public byte[] decrypt(BigInteger[] encryptedMessage) {
		BigInteger n = privateKey.getPrimeQuotient();
		BigInteger d = privateKey.getKeyExponent();

		byte[] message = new byte[encryptedMessage.length];

		for (int i = 0; i < encryptedMessage.length; i++) {
			message[i] = encryptedMessage[i].modPow(d, n).byteValue();
		}

		return message;
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
