package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PositiveNumberValidatorTest {

    private PositiveNumberValidator positiveNumberValidator;

    @Before
    public void setup() {
        positiveNumberValidator = new PositiveNumberValidator();
    }

    @Test
    public void testNegativeNumber() {
        boolean restult = positiveNumberValidator.isPositive(-1);
        Assert.assertThat(restult, is(false));
    }

    @Test
    public void testZeroNumber() {
        boolean restult = positiveNumberValidator.isPositive(0);
        Assert.assertThat(restult, is(false));
    }

    @Test
    public void testPositiveNumber() {
        boolean result = positiveNumberValidator.isPositive(1);
        Assert.assertThat(result, is(true));
    }
}
