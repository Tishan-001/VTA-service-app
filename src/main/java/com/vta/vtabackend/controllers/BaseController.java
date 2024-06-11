package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.ErrorResponse;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.utils.HttpStatusUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(VTAException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(VTAException cyclePosException) {
        HttpStatus httpStatus = HttpStatusUtil.resolveCyclePosHttpStatus(cyclePosException);
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), cyclePosException.getErrorCode(),
                cyclePosException.getErrorMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}

