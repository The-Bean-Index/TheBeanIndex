package com.example.beanIndex.models;

import java.util.Date;

public record AuthRecord(String idToken, Date expiration) {
}
