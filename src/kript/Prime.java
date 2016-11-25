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
import java.util.Random;

public class Prime {

	private BigInteger primeNumber;
	private Random random = new Random();

	/**
	 * Default constructor. Generates its own 512 bit prime number.
	 */
	public Prime() {
		generatePrime();
	}

	/**
	 * Generate the prime number being used.
	 */
	private void generatePrime() {
		primeNumber = new BigInteger(512, Integer.MAX_VALUE, random);
	}

	/**
	 * Get the prime number.
	 * 
	 * @return BigInteger prime number.
	 */
	public BigInteger getPrime() {
		return primeNumber;
	}
}
