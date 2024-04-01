package com.vta.vtabackend.utils;

import java.util.Random;

public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
    public static String getRandomNumbers(int length) {
        String candidateChars = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.substring(0, 10).length())));
        }
        return sb.toString();
    }
}
