package avtech.software.kript;

import java.util.ArrayList;
import java.util.Random;

public class Prime {

	private long primeNumber;
	private boolean prime = false;
	private Random random = new Random();

	public Prime() {
		primeNumber = generatePrime();
	}

	private long generatePrime() {

		// make sure the test number is greater than 2
		long temp = random.nextInt(10000000) + 2;
		long number = 0;

		// make sure test prime isnt negative
		if (temp < 0)
			temp = temp * -1;

		while (!prime) {

			// make sure temp isnt divisible by 2, 3, or 5
			while (temp % 2 == 0 || temp % 3 == 0 || temp % 5 == 0)
				temp--;

			for (long i = 2; i <= (temp / 2); i++) {
				// skip the test if i is divisible by 2
				if (i % 2 == 0)
					continue;
				// skip if divisible by 3
				if (i % 3 == 0)
					continue;
				// skip if divisible by 5
				if (i % 5 == 0)
					continue;

				// if test prime / i is 0, not prime as long as i isnt the test
				// prime
				if (temp % i == 0 && i != temp) {
					prime = false;
					break;
				}

				// if i reaches the half way mark without causing a / = 0
				// signal, number is prime
				if (i == temp / 2) {
					prime = true;
					number = temp;
				}
			}

			temp--;

		}

		return number;
	}

	public long getPrime() {
		return primeNumber;
	}

	public static boolean isCoprime(long e, long etfMod) {
		boolean coprime = false;
		int size = 0;
		ArrayList<Long> eList = new ArrayList<Long>();
		ArrayList<Long> etfModList = new ArrayList<Long>();
		ArrayList<Long> commonDivisors = new ArrayList<Long>();

		/**
		 * generate all divisors in both lists
		 */
		for (long i = 1; i < e; i++) {
			if (e % i == 0) {
				eList.add(i);
			}
		}
		for (long i = 1; i < etfMod; i++) {
			if (etfMod % i == 0) {
				etfModList.add(i);
			}
		}

		/**
		 * determine which list is shorter, and then use that list to reduce
		 * errors
		 */
		size = eList.size();
		if (etfModList.size() < size)
			size = etfModList.size();

		/**
		 * generate a list of common divisors
		 */
		for (int i = 0; i < size; i++) {
			long divisor = eList.get(i);
			if (etfModList.contains(divisor)) {
				commonDivisors.add(divisor);
			}
		}

		/**
		 * check gcd
		 */
		long greatest = 0;
		for (int i = 0; i < commonDivisors.size(); i++) {
			greatest = commonDivisors.get(i);
			if (i > 0 && commonDivisors.get(i) > greatest)
				greatest = commonDivisors.get(i);
		}
		if (greatest == 1)
			coprime = true;
		else
			coprime = false;
		return coprime;
	}
}
