package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

public class IntervalsOverlapDetector {

    public boolean isOverlap(Interval interval1, Interval interval2) {
        if (interval2.getEnd() > interval1.getStart() && interval2.getEnd() < interval1.getEnd()) {
            return true;
        }
        else if (interval1.getStart() > interval2.getStart() && interval1.getEnd() < interval2.getEnd()) {
            return true;
        }
        else if (interval2.getStart() > interval1.getStart() && interval2.getStart() < interval1.getEnd()) {
            return  true;
        }
        else if (interval1.getStart() < interval2.getStart() || interval1.getEnd() < interval2.getEnd()) {
            return false;
        }
        return false;
    }
}
