package avtech.software.kript;

public class PrivateKey extends Key {
	public PrivateKey() {
		generatePrime();
	}

	public void generatePrime() {
		// make sure the test number is greater than 2
		long temp = random.nextInt(10000000) + 2;

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
					key = temp;
					return;
				}
			}

			temp--;

		}
	}
}
