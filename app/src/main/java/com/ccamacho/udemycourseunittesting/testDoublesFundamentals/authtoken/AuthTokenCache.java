package com.ccamacho.udemycourseunittesting.testDoublesFundamentals.authtoken;

public interface AuthTokenCache {

    void cacheAuthToken(String authToken);

    String getAuthToken();
}
