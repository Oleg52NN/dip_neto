package com.example.dip_neto.exeptions;

import org.springframework.http.HttpStatus;

public interface IFException {
    HttpStatus getStatus();

    String getMessage();
}
