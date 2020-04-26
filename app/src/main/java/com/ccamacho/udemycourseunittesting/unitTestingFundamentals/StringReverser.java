package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

public class StringReverser {

    public String reverse(String string) {
        StringBuilder result = new StringBuilder();
        for (int i = string.length() - 1; i >= 0; i--) {
            result.append(string.charAt(i));
        }
        return result.toString();
    }
}
