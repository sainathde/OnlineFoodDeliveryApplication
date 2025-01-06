package com.foodApp.security;

public class Decryption {

    // Static method to decrypt a given string
    public static String decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Subtract 2 from each character
            decrypted.append((char) (c - 2));
        }
        return decrypted.toString();
    }
}
