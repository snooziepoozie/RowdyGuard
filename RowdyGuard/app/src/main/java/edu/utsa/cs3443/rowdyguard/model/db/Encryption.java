package edu.utsa.cs3443.rowdyguard.model.db;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;

public class Encryption {
//    static String readAndDecryptFile(String password, String filename, Context context) throws Exception {
//        byte[] fileContent = Files.readAllBytes(Paths.get(String.valueOf(context.getFilesDir()), filename));
//
//        byte[] salt = new byte[16];
//        System.arraycopy(fileContent, 0, salt, 0, 16);
//        byte[] iv = new byte[12];
//        System.arraycopy(fileContent, 16, iv, 0, 12);
//        byte[] encryptedData = new byte[fileContent.length - 28];
//        System.arraycopy(fileContent, 28, encryptedData, 0, encryptedData.length);
//
//        SecretKey key = deriveKeyFromPassword(password, salt);
//
//        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
//        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
//
//        byte[] decryptedData = cipher.doFinal(encryptedData);
//        return new String(decryptedData);
//    }
//    static void encryptAndWriteToFile(String data, SecretKey key, String filename, Context context) throws Exception {
//        File file = new File(context.getFilesDir(), filename);
//
//        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//        byte[] iv = new byte[12]; // GCM requires a 12-byte IV
//        new SecureRandom().nextBytes(iv);
//        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
//        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
//
//        byte[] encryptedData = cipher.doFinal(data.getBytes());
//
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(generateSalt());            // Save the salt for key derivation
//            fos.write(iv);              // Save the IV for decryption
//            fos.write(encryptedData);   // Save the encrypted data
//        }
//    }
    static SecretKey deriveKeyFromPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec);
    }

    static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    static byte[] encryptData(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, new byte[12]));
        return cipher.doFinal(plainText.getBytes());
    }

    static String decryptData(byte[] cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, new byte[12]));
        return new String(cipher.doFinal(cipherText));
    }
}