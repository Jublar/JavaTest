package com.kenect.kenectspringtest.exception;

/**
 * <p>InvalidInputException class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
public class InvalidInputException extends RuntimeException {
    /**
     * <p>Constructor for InvalidInputException.</p>
     *
     * @param msg a {@link java.lang.String} object.
     */
    public InvalidInputException(String msg) {
        super(msg);
    }
}
