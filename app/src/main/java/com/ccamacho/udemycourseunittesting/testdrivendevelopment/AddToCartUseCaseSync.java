package com.ccamacho.udemycourseunittesting.testdrivendevelopment;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync.EndpointResult;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.CartItemScheme;

public class AddToCartUseCaseSync {

    private final AddToCartHttpEndpointSync mAddToCartHttpEndpointSync;

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    public AddToCartUseCaseSync(AddToCartHttpEndpointSync addToCartHttpEndpointSync) {
        mAddToCartHttpEndpointSync = addToCartHttpEndpointSync;
    }

    public UseCaseResult addToCartSync(String offerId, int amount) {
        EndpointResult result;

        try {
            result = mAddToCartHttpEndpointSync.addToCartSync(new CartItemScheme(offerId, amount));
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }

        switch (result) {
            case SUCCESS:
                return UseCaseResult.SUCCESS;
            case AUTH_ERROR:
            case GENERAL_ERROR:
                return UseCaseResult.FAILURE;
            default:
                throw new RuntimeException("invalid status: " + result);
        }
    }
}
