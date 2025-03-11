package com.spidey.openmusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private int code;

    public CustomException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.code = httpStatus.value();
    }

}