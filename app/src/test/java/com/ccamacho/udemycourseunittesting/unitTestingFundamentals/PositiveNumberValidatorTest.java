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
    public void positive_negativeNumber_falseReturned() {
        boolean restult = positiveNumberValidator.isPositive(-1);
        Assert.assertThat(restult, is(false));
    }

    @Test
    public void positive_zeroNumber_falseReturned() {
        boolean restult = positiveNumberValidator.isPositive(0);
        Assert.assertThat(restult, is(false));
    }

    @Test
    public void positive_positiveNumber_trueReturned() {
        boolean result = positiveNumberValidator.isPositive(1);
        Assert.assertThat(result, is(true));
    }
}
