package com.ccamacho.udemycourseunittesting.unitTestingFundamentals;

public class Interval {

    private final int mStart;
    private final int mEnd;

    public Interval(int start, int end) throws IllegalAccessException {
        if (start >= end) {
            throw new IllegalAccessException("invalid interval range");
        }
        mStart = start;
        mEnd = end;
    }

    public int getStart() {
        return mStart;
    }

    public int getEnd() {
        return mEnd;
    }
}
