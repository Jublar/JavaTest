package com.kenect.kenectspringtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>ElementNotFoundAdvice class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@ControllerAdvice
public class ElementNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String elementNotFoundHandler(ElementNotFoundException ex){
        return ex.getMessage();
    }
}
