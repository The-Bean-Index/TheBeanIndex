package com.bbdgrad.beanIndex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;

public abstract class SecuredCommand {
    @Autowired
    AuthService authService;


    public Availability isUserSignedIn() {
        if (authService.isIdTokenValid()) {
            return Availability.available();
        }

        return Availability.unavailable("You are not logged in. Please log in using the 'login' command");
    }
}
