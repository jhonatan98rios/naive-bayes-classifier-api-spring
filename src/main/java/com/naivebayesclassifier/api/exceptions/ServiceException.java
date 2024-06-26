package com.naivebayesclassifier.api.exceptions;

public class ServiceException extends RuntimeException{
    private static final long servialVersionUID = 1L;
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}