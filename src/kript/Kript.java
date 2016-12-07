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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private PublicKey remotePublicKey;

	/**
	 * Default constructor. Calling this constructor, Kript will generate it's
	 * own prime numbers for key creation.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public Kript() throws NoSuchAlgorithmException {
		// prime1 = new Prime();
		// prime2 = new Prime();
		//
		// primeQuotient = prime1.getPrime().multiply(prime2.getPrime());
		// totient = (prime1.getPrime().subtract(new BigInteger("1")))
		// .multiply((prime2.getPrime().subtract(new BigInteger("1"))));
		// generatePublicKeyPrime();
		// generatePrivateKeyPrime();
		generateKeypair();
		System.out.println("KRIPT: Kript keys created and ready to be used.");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		try {
			Kript k = new Kript();

			final String originalText = "Text to be encrypted !@#$%^&*()_+";
			final byte[] cipherText = k.encrypt(originalText.getBytes());
			final byte[] decrypted = k.decrypt(cipherText);

			// Printing the Original, Encrypted and Decrypted Text
			System.out.println("Original Text: " + originalText);

			StringBuffer retString = new StringBuffer();
			for (int i = 0; i < cipherText.length; ++i) {
				retString.append(Integer.toHexString(0x0100 + (cipherText[i] & 0x00FF)).substring(1));
			}

			System.out.println("Encrypted Text: " + retString);

			String retString1 = new String(decrypted);

			System.out.println("Decrypted Text: " + retString1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates the keypair to be used, assigning them to the appropriate
	 * variables.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             thrown if RSA is unable to be found.
	 */
	private void generateKeypair() throws NoSuchAlgorithmException {
		final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		final KeyPair key = keyGen.generateKeyPair();

		publicKey = key.getPublic();

		privateKey = key.getPrivate();

		// remotePublicKey = key.getPublic();
	}

	/**
	 * Encrypt a byte, returns the long version of it.
	 * 
	 * @param bytes[]
	 *            Byte array to encrypt
	 * @return BigInteger[] of encrypted message
	 * @throws Exception
	 *             if there is an error with the encryption.
	 */
	public byte[] encrypt(byte[] bytes) throws Exception {
		byte[] cipherText = null;
		// get an RSA cipher object and print the provider
		final Cipher cipher = Cipher.getInstance("RSA");
		// encrypt the plain text using the public key
		cipher.init(Cipher.ENCRYPT_MODE, remotePublicKey);
		cipherText = cipher.doFinal(bytes);
		return cipherText;
	}

	/**
	 * Decrypt an encrypted byte, return the long version of the decryption.
	 * 
	 * @param encryptedMessage
	 *            BigInteger[] encrypted byte message
	 * @return byte[] array containing message values
	 */
	public byte[] decrypt(byte[] msg) throws Exception {
		byte[] decrypted = null;
		// get an RSA cipher object and print the provider
		final Cipher cipher = Cipher.getInstance("RSA");

		// decrypt the text using the private key
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		decrypted = cipher.doFinal(msg);

		return decrypted;
	}

	/**
	 * Set the public key of your connection.
	 * 
	 * @param k
	 */
	public void setRemotePublicKey(PublicKey k) {
		remotePublicKey = k;
	}

	/**
	 * Get the public key.
	 * 
	 * @return PublicKey
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}
}
