package com.example.dip_neto.exeptions;

import org.springframework.http.HttpStatus;

public class DataException extends RuntimeException implements IFException{
        private final HttpStatus httpStatus;

        public DataException(String msg, HttpStatus httpStatus) {
            super(msg);
            this.httpStatus = httpStatus;
        }
        @Override
        public HttpStatus getStatus() {
            return httpStatus;
        }
}
