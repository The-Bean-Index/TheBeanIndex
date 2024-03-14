package com.bbdgrad.beanIndex.models;

import java.util.Date;

public record AuthRecord(String idToken, Date expiration) {
}
