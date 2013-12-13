package com.pier.application.service.security.token;

import java.util.Date;

public class AuthToken {
    private final String value;
    private final Date expirationDate;
    private final Long userId;

    public AuthToken(String value, Date expirationDate, Long userId) {
        this.value = value;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Long getUserId() {
        return userId;
    }
}
