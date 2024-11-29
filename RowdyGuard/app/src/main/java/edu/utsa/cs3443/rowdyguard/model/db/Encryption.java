package edu.utsa.cs3443.rowdyguard.model.db;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Basic module for handling encryption and decryption
 * @author Jonathan Beierle
 */

public class Encryption {

    /**
     * Method to derive the encryption key given a password
     * @param password Password to use for key derivation
     * @param salt Salt to use for deriving the key
     * @return A SecretKey object used for further encryption/decryption operations
     * @throws Exception Occurs if an error occurs during the key derivation process
     */
    static SecretKey deriveKeyFromPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec);
    }

    /**
     * Method to encrypt data
     * @param plainText Plaintext string to encrypt
     * @param key Key to use for encryption
     * @return Returns a byte array containing the ciphertext
     * @throws Exception Occurs if the data cannot be encrypted
     */
    static byte[] encryptData(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, new byte[12]));
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * Method to decrypt ciphertext into plaintext data
     * @param cipherText Encrypted data to decrypt
     * @param key Secret key to use to decrypt ciphertext
     * @return String containing the decrypted data
     * @throws Exception Occurs if the data could not be successfully decrypted
     */
    static String decryptData(byte[] cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, new byte[12]));
        return new String(cipher.doFinal(cipherText));
    }
}