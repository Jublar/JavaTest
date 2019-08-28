package com.kenect.kenectspringtest.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
