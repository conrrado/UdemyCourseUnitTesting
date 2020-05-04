package com.ccamacho.udemycourseunittesting.testDoublesFundamentals.username;

public class ServerUsernameValidator implements UsernameValidator {

    public boolean isValidUsername(String username) {
        try {
            Thread.sleep(1000);
            throw new RuntimeException("no network connection");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
