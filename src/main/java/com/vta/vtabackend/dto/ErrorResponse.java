package com.vta.vtabackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private Integer httpStatus;

    private Integer errorCode;

    private String errorMessage;

    public ErrorResponse(Integer httpStatus, Integer errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
