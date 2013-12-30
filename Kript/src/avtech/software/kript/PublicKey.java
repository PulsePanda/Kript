package avtech.software.kript;

public class PublicKey extends Key {
	public PublicKey(PrivateKey k1, PrivateKey k2) {
		key = k1.getKey() * k2.getKey() * 2;
	}
}
