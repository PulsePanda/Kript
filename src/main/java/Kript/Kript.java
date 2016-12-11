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

package main.java.Kript;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

/**
 * TO ENCODE, MUST SUBMIT A BYTE ARRAY OF THE MESSAGE
 */

public class Kript {

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private PublicKey remotePublicKey;

	/**
	 * Default constructor.
	 *
	 * @throws NoSuchAlgorithmException
	 *             if there is an issue with creating the RSA keys.
	 */
	public Kript() throws NoSuchAlgorithmException {
		generateKeypair();
		System.out.println("KRIPT: Kript keys created and ready to be used.");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		try {
			Kript k = new Kript();

			String originalText = "Text to be encrypted !@#$%^&*()_+";
			byte[] cipherText = k.encrypt(originalText.getBytes());
			byte[] decrypted = k.decrypt(cipherText);

			// Printing the Original, Encrypted and Decrypted Text
			System.out.println("Original Text: " + originalText);

			System.out.println("Encrypted Text: " + cipherText);

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

//		 remotePublicKey = key.getPublic();
	}

	/**
	 * Encrypt a byte, returns the long version of it.
	 *
	 * @param bytes[]
	 *            Byte array to encrypt
	 * @return byte[] containing encrypted message
	 * @throws Exception
	 *             if there is an error with the encryption.
	 */
	public byte[] encrypt(byte[] bytes) throws Exception {
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, remotePublicKey);
		return blockCipher(bytes, cipher, Cipher.ENCRYPT_MODE);
	}

	/**
	 * Decrypt an encrypted byte, return the long version of the decryption.
	 *
	 * @param encryptedMessage
	 *            byte[] containing encrypted byte message
	 * @return byte[] array containing message values
	 *
	 * @throws Exception
	 *             if there is an issue creating RSA cipher to decrypt.
	 */
	public byte[] decrypt(byte[] encryptedMessage) throws Exception {
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return blockCipher(encryptedMessage, cipher, Cipher.DECRYPT_MODE);
	}

	/**
	 * Handle the RSA side of things. Allows for items larger than the block
	 * size to be encoded.
	 *
	 * @param bytes
	 *            byte[] array containing either plain or encoded data.
	 * @param cipher
	 *            Cipher object containing the mode, and related key.
	 * @return byte[] with the appropriate encoded/plain data.
	 */
	private byte[] blockCipher(byte[] bytes, Cipher cipher, int mode) {
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
		byte[] toReturn = new byte[0];
		// if we encrypt we use 100 byte long blocks. Decryption requires 128
		// byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE) ? 100 : 128;

		// another buffer. this one will hold the bytes that have to be modified
		// in this step
		byte[] buffer = new byte[length];

		for (int i = 0; i < bytes.length; i++) {

			// if we filled our buffer array we have our block ready for de- or
			// encryption
			if ((i > 0) && (i % length == 0)) {
				// execute the operation
				try {
					scrambled = cipher.doFinal(buffer);
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// add the result to our total result.
				toReturn = append(toReturn, scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the
				// bytes array we shorten it.
				if (i + length > bytes.length) {
					newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i % length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen
		// when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot"
		// the last 10 bytes. they are in the buffer array
		try {
			scrambled = cipher.doFinal(buffer);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// final step before we can return the modified data.
		toReturn = append(toReturn, scrambled);

		return toReturn;
	}

	private byte[] append(byte[] prefix, byte[] suffix) {
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i = 0; i < prefix.length; i++) {
			toReturn[i] = prefix[i];
		}
		for (int i = 0; i < suffix.length; i++) {
			toReturn[i + prefix.length] = suffix[i];
		}
		return toReturn;
	}

	/**
	 * Set the public key of your connection.
	 *
	 * @param k
	 *            remote public key
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
