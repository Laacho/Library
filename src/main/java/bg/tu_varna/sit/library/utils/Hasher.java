package bg.tu_varna.sit.library.utils;


import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hasher {
    private static final Logger log = Logger.getLogger(Hasher.class);

    //https://www.geeksforgeeks.org/sha-512-hash-in-java/
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Password hashing failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        String hashedInputPassword = hashPassword(enteredPassword);
        return hashedInputPassword.equals(storedHash);
    }

}
