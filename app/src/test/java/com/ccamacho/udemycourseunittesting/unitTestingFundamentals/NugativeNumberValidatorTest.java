package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NugativeNumberValidatorTest {

    private NegativeNumberValidator negativeNumberValidator;

    @Before
    public void setup() {
        negativeNumberValidator = new NegativeNumberValidator();
    }

    @Test
    public void negative_negativeNumber_trueReturned() {
        boolean result = negativeNumberValidator.isNegative(-1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void negative_zeroNumber_falseReturned() {
        boolean result = negativeNumberValidator.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void negative_positiveNumber_falseReturned() {
        boolean result = negativeNumberValidator.isNegative(1);
        Assert.assertThat(result, is(false));
    }
}
