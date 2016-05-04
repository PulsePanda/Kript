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

public class Binary {
	public static String toBinaryString(String s, boolean withSpaces) {
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}

			if (withSpaces)
				binary.append(' ');
		}

		return binary.toString();

	}

	public static String stringFromBinary(String s, boolean stringHasSpaces) {

		String[] ss = null;

		if (!stringHasSpaces) {
			ss = addSpacesToBinary(s).split(" ");
		} else {
			ss = s.split(" ");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ss.length; i++) {
			sb.append((char) Integer.parseInt(ss[i], 2));
		}

		return sb.toString();
	}

	private static String addSpacesToBinary(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		int length = s.length();
		for (int i = 0; i < length; i++) {
			if (i % 9 == 0 && i != 0) {
				sb.insert(i - 1, " ");
				length = sb.length();
			}
		}

		return sb.toString();
	}
}
