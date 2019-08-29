package com.kenect.kenectspringtest.exception;

/**
 * <p>ElementNotFoundException class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
public class ElementNotFoundException extends RuntimeException {
    /**
     * <p>Constructor for ElementNotFoundException.</p>
     *
     * @param msg a {@link java.lang.String} object.
     */
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
