package com.bbdgrad.thebeanindex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class YearNotFoundException extends ResponseStatusException{
    
    public YearNotFoundException(int year) {
        super(HttpStatus.NOT_FOUND, "Year not found: " + year);
    }
}
