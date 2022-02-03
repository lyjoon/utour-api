package com.utour.exception;

/**
 * 내부 에러 (500)
 */
public class InternalException extends RuntimeException {

    public InternalException(){
        super();
    }

    public InternalException(String message){
        super(message);
    }

    public InternalException(Throwable throwable){
        super(throwable);
    }

    public InternalException(String message, Throwable throwable){
        super(message, throwable);
    }
}
