package com.vta.vtabackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class VTAException extends RuntimeException{

    private Type type;

    private String errorMessage;

    private Integer errorCode;

    public VTAException(Type type, String errorMessage, Integer errorCode) {
        super(errorMessage);
        this.type = type;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public enum Type {
        NOT_FOUND, UNAUTHORIZED, UNEXPECTED_ERROR, DATA_ERROR, EXTERNAL_SYSTEM_ERROR, VALIDATION_ERROR, ACCESS_DENIED,
        CONFIGURATION_ERROR
    }
}
