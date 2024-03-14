package com.bbdgrad.beanIndex;

import com.bbdgrad.beanIndex.models.AuthRecord;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthService {
    private String idToken;
    private Date expiration;


    public void setAuth(AuthRecord authRecord) {
        this.idToken = authRecord.idToken();
        this.expiration = authRecord.expiration();
    }


    public String getIdToken() {
        return idToken;
    }


    public Boolean isIdTokenValid() {
        if (Objects.isNull(idToken)) {
            return false;
        }

        if (new Date().after(expiration)) {
            this.logout();
            return false;
        }

        return true;
    }


    public void logout() {
        idToken = null;
        expiration = null;
    }
}
