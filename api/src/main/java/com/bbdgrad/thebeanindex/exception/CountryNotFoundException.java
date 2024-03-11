package com.bbdgrad.thebeanindex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CountryNotFoundException extends ResponseStatusException{
    
    public CountryNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Country does not exist: " + name);
    }
}
