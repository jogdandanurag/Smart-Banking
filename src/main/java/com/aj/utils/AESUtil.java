package com.aj.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128; // AES 128-bit key size
    private static final int IV_SIZE = 12;  // GCM IV size (recommended 12 bytes)
    private static final int TAG_SIZE = 128; // GCM authentication tag size

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * Generate a new random AES secret key securely.
     * @return Base64 encoded secret key
     */
    public static String generateSecretKey() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[KEY_SIZE / 8]; // 128-bit key
            secureRandom.nextBytes(keyBytes);
            return Base64.getEncoder().encodeToString(keyBytes);
        } catch (Exception e) {
            logger.error("Error generating secret key", e);
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    /**
     * Encrypt the plain text using the provided secret key (Base64-encoded).
     * @param plainText The plain text to encrypt
     * @param base64Key The Base64 encoded secret key
     * @return Encrypted data in Base64 format
     */
    public static String encrypt(String plainText, String base64Key) {
        try {
            SecretKey secretKey = getSecretKeyFromBase64(base64Key);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Generate random IV
            byte[] iv = generateIV();
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_SIZE, iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            // Encrypt the plain text
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Concatenate IV and cipher text
            byte[] encryptedData = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedData, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

            // Return encrypted data in Base64 format
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            logger.error("Error while encrypting", e);
            throw new RuntimeException("Error while encrypting: " + e.getMessage(), e);
        }
    }

    /**
     * Decrypt the encrypted text using the provided secret key (Base64-encoded).
     * @param encryptedText The encrypted text in Base64 format
     * @param base64Key The Base64 encoded secret key
     * @return Decrypted plain text
     */
    public static String decrypt(String encryptedText, String base64Key) {
        try {
            // Decode the Base64-encoded encrypted text
            byte[] decoded = Base64.getDecoder().decode(encryptedText);

            if (decoded.length < IV_SIZE) {
                throw new RuntimeException("Invalid encrypted data: not enough data for IV.");
            }

            // Extract IV from the beginning of the data
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(decoded, 0, iv, 0, IV_SIZE);

            // Extract cipher text from the rest of the data
            byte[] cipherText = new byte[decoded.length - IV_SIZE];
            System.arraycopy(decoded, IV_SIZE, cipherText, 0, cipherText.length);

            // Convert the base64 key to SecretKey object
            SecretKey secretKey = getSecretKeyFromBase64(base64Key);

            // Initialize cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_SIZE, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            // Decrypt the cipher text
            byte[] decryptedData = cipher.doFinal(cipherText);

            // Return the decrypted data as string
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Error while decrypting", e);
            throw new RuntimeException("Error while decrypting: " + e.getMessage(), e);
        }
    }

    /**
     * Generate a secure random IV for AES-GCM.
     * @return IV as byte array
     */
    private static byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Convert Base64 encoded key to SecretKey object.
     * @param base64Key The Base64 encoded key
     * @return SecretKey object
     */
    private static SecretKey getSecretKeyFromBase64(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    /**
     * Example of getting a fixed secret key (stored securely in an environment variable or config).
     * @return Base64 encoded secret key
     */
    public static String getSecureSecretKey() {
        // This can be fetched from a secure location (Key Vault, environment variable, etc.)
        return System.getenv("SECURE_AES_SECRET_KEY");
    }

    /**
     * Method to get the Base64 encoded secret key (example).
     * @return Example Base64 encoded secret key
     */
    public static String getExampleSecretKey() {
        // This is for demonstration, do not use hardcoded keys in production.
        return "0123456789abcdef0123456789abcdef"; // Replace with actual dynamic key
    }
}