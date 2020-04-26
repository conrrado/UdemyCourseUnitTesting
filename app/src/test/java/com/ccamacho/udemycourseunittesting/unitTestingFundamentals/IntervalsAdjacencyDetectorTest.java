package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class IntervalsAdjacencyDetectorTest {

    private IntervalsAdjacencyDetector intervalsAdjacencyDetector;

    @Before
    public void setup() {
        intervalsAdjacencyDetector = new IntervalsAdjacencyDetector();
    }

    @Test
    public void isOverlap_interval1BeforeAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(5, 8);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void isOverlap_interval1AfterAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-3, -1);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void isAdjacency_interval1BeforeInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(8, 12);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacency_interval1AfterInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-10, -3);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacency_internal1ContainedWithinInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-4, 12);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacency_interval1ContainsInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(0, 3);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacency_interval1OverlapsInterval2OnStart_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(3, 12);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacency_interval1OverlapsInterval2OnEnd_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-4, 4);
        boolean result = intervalsAdjacencyDetector.isAdjacency(interval1, interval2);
        Assert.assertThat(result, is(false));
    }
}
