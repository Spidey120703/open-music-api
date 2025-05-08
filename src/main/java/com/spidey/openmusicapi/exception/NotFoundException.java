package com.spidey.openmusicapi.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GlobalException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "不存在");
    }
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
