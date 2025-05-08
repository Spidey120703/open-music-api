package com.spidey.openmusicapi.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GlobalException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "无权限");
    }
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
