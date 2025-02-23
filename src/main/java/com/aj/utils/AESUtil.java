package com.aj.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int IV_SIZE = 12; // Recommended size for GCM mode
    private static final int TAG_SIZE = 128;

    private static final String SECRET_KEY = "0123456789abcdef0123456789abcdef"; // Fixed 256-bit secret key (Base64-encoded)

    // Encrypt the plain text using the default secret key
    public static String encrypt(String plainText) {
        return encrypt(plainText, SECRET_KEY);
    }

    // Decrypt the encrypted text using the default secret key
    public static String decrypt(String encryptedText) {
        return decrypt(encryptedText, SECRET_KEY);
    }

    // Encrypt the plain text with the provided key (Base64 encoded key)
    public static String encrypt(String plainText, String base64Key) {
        try {
            SecretKey secretKey = getSecretKeyFromBase64(base64Key);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Generate random IV
            byte[] iv = generateIV();
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_SIZE, iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            // Perform encryption
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Concatenate IV and cipher text
            byte[] encryptedData = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedData, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

            // Return encrypted data in Base64
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage(), e);
        }
    }

    // Decrypt the encrypted text with the provided key (Base64 encoded key)
    public static String decrypt(String encryptedText, String base64Key) {
        try {
            // Decode the Base64-encoded encrypted text
            byte[] decoded = Base64.getDecoder().decode(encryptedText);

            if (decoded.length < IV_SIZE) {
                throw new RuntimeException("Invalid encrypted data: not enough data for IV.");
            }

            // Extract IV from the beginning
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(decoded, 0, iv, 0, IV_SIZE);

            // Extract cipher text from the rest of the decoded data
            byte[] cipherText = new byte[decoded.length - IV_SIZE];
            System.arraycopy(decoded, IV_SIZE, cipherText, 0, cipherText.length);

            // Convert the base64 key to a SecretKey object
            SecretKey secretKey = getSecretKeyFromBase64(base64Key);

            // Initialize cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_SIZE, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            // Perform decryption
            byte[] decryptedData = cipher.doFinal(cipherText);

            // Convert decrypted bytes to string
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage(), e);
        }
    }

    // Generate a random IV for AES-GCM
    private static byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // Get the secret key from Base64 string
    private static SecretKey getSecretKeyFromBase64(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    // Return the fixed secret key (Base64 encoded)
    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
