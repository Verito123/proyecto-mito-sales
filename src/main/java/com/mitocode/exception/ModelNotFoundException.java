package com.mitocode.exception;


//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends RuntimeException{
    public ModelNotFoundException(String message){
        super(message);

    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message);
    }
}
