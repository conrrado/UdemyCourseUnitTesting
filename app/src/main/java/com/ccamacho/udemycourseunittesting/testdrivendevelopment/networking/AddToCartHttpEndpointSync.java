package com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;

public interface AddToCartHttpEndpointSync {

    EndpointResult addToCartSync(CartItemScheme cartItemScheme) throws NetworkErrorException;

    enum EndpointResult {
        SUCCESS,
        AUTH_ERROR,
        GENERAL_ERROR
    }
}
