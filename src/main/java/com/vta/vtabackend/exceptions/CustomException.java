package com.vta.vtabackend.exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException(String msg) {
        super(msg);
    }
}

