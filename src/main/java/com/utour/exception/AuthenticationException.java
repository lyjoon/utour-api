package com.utour.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String message){
        super(message);
    }

    public AuthenticationException(Throwable throwable){
        super(throwable);
    }

    public AuthenticationException(String message, Throwable throwable){
        super(message, throwable);
    }
}
