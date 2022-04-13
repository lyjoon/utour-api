package com.utour.exception;

public class PasswordIncorrectException extends RuntimeException{

    public PasswordIncorrectException(){
        super();
    }

    public PasswordIncorrectException(String message){
        super(message);
    }

    public PasswordIncorrectException(Throwable throwable){
        super(throwable);
    }

    public PasswordIncorrectException(String message, Throwable throwable){
        super(message, throwable);
    }
}
