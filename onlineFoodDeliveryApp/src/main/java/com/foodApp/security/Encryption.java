package com.foodApp.security;

public class Encryption {

    // Static method to encrypt a given string
    public static String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Add 2 to each character
            encrypted.append((char) (c + 2));
        }
        return encrypted.toString();
    }
}
