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

package Kript;

import java.io.Serializable;

public class PublicKey extends Key implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long n;
	private long e;

	public PublicKey(long n, long e) {
		this.n = n;
		this.e = e;
	}

	public long getN() {
		return n;
	}

	public long getE() {
		return e;
	}

	public void print() {
		System.out.println("publicKey: n: " + n + " e: " + e);
	}
}
