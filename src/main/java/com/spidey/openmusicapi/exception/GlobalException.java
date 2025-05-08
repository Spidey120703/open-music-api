package com.spidey.openmusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private int code;

    public GlobalException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public GlobalException(HttpStatus httpStatus, String message) {
        super(message);
        this.code = httpStatus.value();
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }

}