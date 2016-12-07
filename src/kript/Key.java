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

import java.io.Serializable;
import java.math.BigInteger;

public class Key implements Serializable {

	private BigInteger primeQuotient;
	private BigInteger keyExponent;

	/**
	 * Default Constructor.
	 * 
	 * @param primeQuotient
	 * @param keyExponent
	 */
	public Key(BigInteger primeQuotient, BigInteger keyExponent) {
		this.primeQuotient = primeQuotient;
		this.keyExponent = keyExponent;
	}

	/**
	 * Get the Prime Quotient
	 * 
	 * @return BigInteger Prime Quotient
	 */
	public BigInteger getPrimeQuotient() {
		return primeQuotient;
	}

	/**
	 * Get the Key's Exponent
	 * 
	 * @return BigInteger Key Exponent
	 */
	public BigInteger getKeyExponent() {
		return keyExponent;
	}
}
