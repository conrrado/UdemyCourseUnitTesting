package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

public class IntervalsAdjacencyDetector {

    public boolean isAdjacency(Interval interval1, Interval interval2) {
        return interval1.getEnd() == interval2.getStart() || interval1.getStart() == interval2.getEnd();
    }
}
