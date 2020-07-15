package com.spreedly.client.models;

public class Range {
    public final int last;
    public final int first;

    public Range(int first, int last) {
        this.first = first;
        this.last = last;
    }

    boolean inRange(int number) {
        return first <= number && last >= number;
    }
}
