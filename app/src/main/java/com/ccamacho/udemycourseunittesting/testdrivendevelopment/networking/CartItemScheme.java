package com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking;

public class CartItemScheme {

    private final String offerId;
    private final int amount;

    public CartItemScheme(String offerId, int amount) {
        this.offerId = offerId;
        this.amount = amount;
    }

    public String getOfferId() {
        return offerId;
    }

    public int getAmount() {
        return amount;
    }
}
