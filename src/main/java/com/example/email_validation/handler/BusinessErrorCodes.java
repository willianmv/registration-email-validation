package com.example.email_validation.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code"),
    INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304, HttpStatus.BAD_REQUEST, "Login and / or password is incorrect");

    @Getter
    private final int code;

    @Getter
    private final String description;

    @Getter
    private final HttpStatus statusCode;

    BusinessErrorCodes(int code, HttpStatus statusCode, String description) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }
}
