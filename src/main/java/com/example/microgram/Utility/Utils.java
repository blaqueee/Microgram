package com.example.microgram.Utility;

public class Utils {
    public static String encode(String text, int length) {
        var array = text.toCharArray();
        var sb = new StringBuilder();
        for (char c : array) {
            c += length;
            sb.append(c);
        }
        return sb.toString();
    }

    public static String decode(String encoded, int length) {
        var array = encoded.toCharArray();
        var sb = new StringBuilder();
        for (char c : array) {
            c -= length;
            sb.append(c);
        }
        return sb.toString();
    }
}
