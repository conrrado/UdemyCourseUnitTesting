package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringReverserTest {

    private StringReverser stringReverser;

    @Before
    public void setup() {
        stringReverser = new StringReverser();
    }

    @Test
    public void reverse_emptyString_emptyStringReturned() throws Exception {
        String result = stringReverser.reverse("");
        Assert.assertThat(result, is(""));
    }

    @Test
    public void reverse_singleCharacter_sameStringReturned() throws Exception {
        String result = stringReverser.reverse("a");
        Assert.assertThat(result, is("a"));
    }

    @Test
    public void reverse_longString_reversedStringReturned() throws Exception {
        String result = stringReverser.reverse("Conrrado Camacho");
        Assert.assertThat(result, is("ohcamaC odarrnoC"));
    }
}
