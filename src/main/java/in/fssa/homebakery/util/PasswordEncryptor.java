package in.fssa.homebakery.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryptor {
	
	public static String encrypt(String plainText, String secretKey) throws Exception {
	    // Generate a secret key from the provided string
	    SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");

	    // Create a cipher instance
	    Cipher cipher = Cipher.getInstance("AES");

	    // Initialize the cipher for encryption
	    cipher.init(Cipher.ENCRYPT_MODE, key);

	    // Encrypt the plaintext
	    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

	    // Encode the encrypted bytes as a Base64 string
	    return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decrypt(String encryptedText, String secretKey) throws Exception {
	    // Generate a secret key from the provided string
	    SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");

	    // Create a cipher instance
	    Cipher cipher = Cipher.getInstance("AES");

	    // Initialize the cipher for decryption
	    cipher.init(Cipher.DECRYPT_MODE, key);

	    // Decode the Base64 encoded string
	    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

	    // Decrypt the ciphertext
	    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

	    // Convert the decrypted bytes to a string
	    return new String(decryptedBytes);
	}
	
}
