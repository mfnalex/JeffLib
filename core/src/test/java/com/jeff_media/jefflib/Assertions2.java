package com.jeff_media.jefflib;

public class Assertions2 {

    public static void assertBetween(double min, double max, double actual) {
        if(actual < min || actual > max) {
            throw new AssertionError("Expected " + actual + " to be between " + min + " and " + max);
        }
    }

}
