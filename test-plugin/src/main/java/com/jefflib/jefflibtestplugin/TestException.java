package com.jefflib.jefflibtestplugin;

public class TestException extends RuntimeException {
    public TestException(String s) {
        super(s);
    }
    public TestException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
