import Kript.Kript;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Austin on 2/3/2017.
 */
public class KriptTest {

    @Test
    public void mainTest() throws Exception {
        Kript k = new Kript();
        k.setRemotePublicKey(k.getPublicKey()); // create a false client public key for testing
        String originalText = "Text to be encrypted!?/.,<>!@#$%^&*()_+-=1234567890";

        byte[] cipherText = k.encrypt(originalText.getBytes());

        assertNotSame(cipherText, originalText.getBytes());

        byte[] decrypted = k.decrypt(cipherText);

        String decryptedString = new String(decrypted);

        assertNotSame(cipherText, decrypted);
        assertArrayEquals(originalText.toCharArray(), decryptedString.trim().toCharArray());
    }
}
