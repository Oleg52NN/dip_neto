package com.example.dip_neto.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException implements IFException{
    private final HttpStatus httpStatus;

    public AuthException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
