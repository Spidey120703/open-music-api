package com.spidey.openmusicapi.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GlobalException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "未授权");
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
