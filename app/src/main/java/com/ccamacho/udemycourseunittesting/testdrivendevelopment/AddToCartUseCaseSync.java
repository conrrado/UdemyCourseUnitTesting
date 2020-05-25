package com.ccamacho.udemycourseunittesting.testdrivendevelopment;

import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync;

public class AddToCartUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    public AddToCartUseCaseSync(AddToCartHttpEndpointSync addToCartHttpEndpointSync) {
    }

    public UseCaseResult addToCartSync(String offerId, int amount) {
        throw new RuntimeException();
    }
}
