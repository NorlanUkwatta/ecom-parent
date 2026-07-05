package com.techmart.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

public class SecurityUtil {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static String generatePayHereHash(String merchantId, String orderId, double amount, String currency, String merchantSecret) {
        try {
            String formattedAmount = String.format(Locale.ENGLISH, "%.2f", amount);

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] secretBytes = md.digest(merchantSecret.getBytes());
            String hashedSecret = String.format("%032x", new BigInteger(1, secretBytes)).toUpperCase();

            String rawData = merchantId + orderId + formattedAmount + currency + hashedSecret;
            byte[] hashBytes = md.digest(rawData.getBytes());

            return String.format("%032x", new BigInteger(1, hashBytes)).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: MD5 algorithm not found for PayHere hash.", e);
        }
    }

    public static boolean verifyPayHereHash(String merchantId, String orderId, String payhereAmount, String payhereCurrency, String statusCode, String md5sig, String merchantSecret) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] secretBytes = md.digest(merchantSecret.getBytes());
            String hashedSecret = String.format("%032x", new BigInteger(1, secretBytes)).toUpperCase();

            String rawData = merchantId + orderId + payhereAmount + payhereCurrency + statusCode + hashedSecret;
            byte[] hashBytes = md.digest(rawData.getBytes());
            String generatedSig = String.format("%032x", new BigInteger(1, hashBytes)).toUpperCase();

            return generatedSig.equals(md5sig.toUpperCase());

        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}