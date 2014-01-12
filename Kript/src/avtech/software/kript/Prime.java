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

	public static boolean isCoprime(long a, long b) {
		boolean coprime = false;
		long size;
		ArrayList<Long> divisorsA = new ArrayList<Long>();
		ArrayList<Long> divisorsB = new ArrayList<Long>();

		// determine the size variable, filling it with the value of the smaller
		// `long`
		size = a;
		if (size > b)
			size = b;

		// generate a list of divisors for each variable
		for (long i = 1; i < size; i++) {
			if (a % i == 0)
				divisorsA.add(i);
			if (b % i == 0)
				divisorsB.add(i);
		}

		long largest = 0;
		for (int i = 0; i < divisorsA.size(); i++) {
			long temp = divisorsA.get(i);

			if (divisorsB.contains(temp)) {
				largest = temp;
			}
		}

		if (largest == 1)
			coprime = true;
		else
			coprime = false;

		return coprime;
	}

	public void print() {
		System.out.println(primeNumber);
	}
}
