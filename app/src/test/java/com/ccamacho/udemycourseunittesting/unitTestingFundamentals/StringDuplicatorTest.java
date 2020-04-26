package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringDuplicatorTest {

    private StringDuplicator stringDuplicator;

    @Before
    public void setup() {
        stringDuplicator = new StringDuplicator();
    }

    @Test
    public void duplicate_emptyString_emptyStringReturned() throws Exception {
        String result = stringDuplicator.duplicate("");
        Assert.assertThat(result, is(""));
    }

    @Test
    public void duplicate_singleCharacter_duplicatedStringReturned() throws Exception {
        String result = stringDuplicator.duplicate("a");
        Assert.assertThat(result, is("aa"));
    }

    @Test
    public void duplicate_longCharacer_duplicatedStringReturned() throws Exception {
        String result = stringDuplicator.duplicate("Conrrado Camacho");
        Assert.assertThat(result, is("Conrrado CamachoConrrado Camacho"));
    }
}
