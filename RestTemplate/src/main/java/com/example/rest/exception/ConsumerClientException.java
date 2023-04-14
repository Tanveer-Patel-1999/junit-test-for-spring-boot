package com.example.rest.exception;

public class ConsumerClientException extends RuntimeException{

    private static final long serialVersionUID = -7210431808899939306L;

    public ConsumerClientException(String message)
    {
        super(message);
    }


    public ConsumerClientException(String message, Throwable cause)
    {
        super(message,cause);
    }

    public ConsumerClientException(Throwable cause) {
        super(cause);
    }

}
