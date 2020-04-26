package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class IntervalsOverlapDetectorTest {

    IntervalsOverlapDetector intervalsOverlapDetector;

    @Before
    public void setup() {
        intervalsOverlapDetector = new IntervalsOverlapDetector();
    }

    // interval 1 is before interval 2

    @Test
    public void isOverlap_interval1BeforeInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(8, 12);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    // interval 1 is after interval 2

    @Test
    public void isOverlap_interval1AfterInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-10, -3);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    // interval 1 opverlaps interval 2 on start

    @Test
    public void isOverlap_interval1OverlapsInterval2OnStart_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(3, 12);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    // interval 1 overlaps interval 2 on end

    @Test
    public void isOverlap_interval1OverlapsInterval2OnEnd_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-4, 4);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    // interval 1 is contained within interval 2

    @Test
    public void isOverlap_internal1ContainedWithinInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-4, 12);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    // interval 1 contains interval 2

    @Test
    public void isOverlap_interval1ContainsInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(0, 3);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void isOverlap_interval1BeforeAdjacentInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(5, 8);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isOverlap_interval1AfterAdjacentInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-3, -1);
        boolean result = intervalsOverlapDetector.isOverlap(interval1, interval2);
        Assert.assertThat(result, is(false));
    }
}
