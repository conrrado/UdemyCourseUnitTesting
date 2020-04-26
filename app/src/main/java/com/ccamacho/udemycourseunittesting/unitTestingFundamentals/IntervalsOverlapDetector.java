package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

public class IntervalsOverlapDetector {

    public boolean isOverlap(Interval interval1, Interval interval2) {
//        if (interval1.getEnd() <= interval2.getStart() || interval1.getStart() >= interval2.getEnd()) {
//            return false;
//        }
//        return true;
        return interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd();
    }
}
