package com.aj.service;

import org.springframework.stereotype.Service;

import com.aj.utils.AESUtil;

@Service
public class EncryptionService {

    private static final String AES_KEY = System.getenv("AES_SECRET_KEY"); // Load AES key from environment variables

    // Encrypts the given plain text data using AESUtil
    public String encryptData(String data) {
        return AESUtil.encrypt(data, AES_KEY);
    }

    // Decrypts the encrypted data using AESUtil
    public String decryptData(String encryptedData) {
        try {
            // Decrypt the data using the AESUtil and the AES key
            return AESUtil.decrypt(encryptedData, AES_KEY);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption: " + e.getMessage(), e);
        }
    }
}
