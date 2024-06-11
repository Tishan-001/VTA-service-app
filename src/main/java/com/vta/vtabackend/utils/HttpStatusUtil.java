package com.vta.vtabackend.utils;

import com.vta.vtabackend.exceptions.VTAException;
import org.springframework.http.HttpStatus;

public class HttpStatusUtil {
    public static HttpStatus resolveCyclePosHttpStatus(VTAException vtaException) {
        HttpStatus httpStatus;
        // resolving the status code to https error code
        switch (vtaException.getType()) {
            case NOT_FOUND:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case UNAUTHORIZED:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case DATA_ERROR:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case EXTERNAL_SYSTEM_ERROR:
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case UNEXPECTED_ERROR:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case CONFIGURATION_ERROR:
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            default:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
        }
        return httpStatus;
    }
}
