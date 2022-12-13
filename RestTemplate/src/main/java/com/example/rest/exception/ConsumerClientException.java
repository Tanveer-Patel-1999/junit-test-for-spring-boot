package com.example.rest.exception;

public class ConsumerClientException extends RuntimeException{

    private static final long serialVersionUID = -7210431808899939306L;

    public ConsumerClientException(String msg)
    {
        super(msg);
    }


    public ConsumerClientException(String msg, Throwable cause)
    {
        super(msg,cause);
    }

    public ConsumerClientException(Throwable cause)
    {
        super(cause);
    }
}
