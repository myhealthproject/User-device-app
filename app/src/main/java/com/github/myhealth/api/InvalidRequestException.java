package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String message){
        super(message);
    }
}
