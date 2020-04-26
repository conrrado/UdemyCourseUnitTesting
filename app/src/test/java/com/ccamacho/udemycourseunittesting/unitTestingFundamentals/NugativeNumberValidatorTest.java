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
    public void testNegativeNumber() {
        boolean result = negativeNumberValidator.isNegative(-1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void testZeroNumber() {
        boolean result = negativeNumberValidator.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void testPositiveNumber() {
        boolean result = negativeNumberValidator.isNegative(1);
        Assert.assertThat(result, is(false));
    }
}
