package com.spidey.openmusicapi.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends GlobalException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "无权限");
    }
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
