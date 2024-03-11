package com.bbdgrad.thebeanindex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GDPinBeanNotFoundException extends ResponseStatusException{
    
    public GDPinBeanNotFoundException(String country, int year) {
        super(HttpStatus.NOT_FOUND, "Cannot find country " + country + " year " + year);
    }
}
