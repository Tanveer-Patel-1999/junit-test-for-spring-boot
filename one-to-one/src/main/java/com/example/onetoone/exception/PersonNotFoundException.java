package com.example.onetoone.exception;

public class PersonNotFoundException extends IllegalArgumentException{
    private static final long personId = 1l;
    public PersonNotFoundException(String message)
    {
        super(message);
    }
}
