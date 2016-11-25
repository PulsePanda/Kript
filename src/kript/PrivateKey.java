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

public class PrivateKey extends Key {

	private long primeQuotient;
	private long privateKeyExponent;

	/**
	 * Default Constructor.
	 * 
	 * @param primeQuotient
	 * @param privateKeyExponent
	 */
	public PrivateKey(long primeQuotient, long privateKeyExponent) {
		this.primeQuotient = primeQuotient;
		this.privateKeyExponent = privateKeyExponent;
	}

	/**
	 * Get the Prime Quotient
	 * 
	 * @return long Prime Quotient
	 */
	public long getPrimeQuotient() {
		return primeQuotient;
	}

	/**
	 * Get the Private Key's Exponent
	 * 
	 * @return long Private Key Exponent
	 */
	public long getPrivateKeyExponent() {
		return privateKeyExponent;
	}

	/**
	 * Print the key for testing.
	 */
	public void print() {
		System.out.println("privateKey: n: " + primeQuotient + " d: " + privateKeyExponent);
	}
}
