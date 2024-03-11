package com.bbdgrad.thebeanindex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BeanNotFoundException extends ResponseStatusException{
   
    public BeanNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Bean name does not exist: " + name);
    }
}
