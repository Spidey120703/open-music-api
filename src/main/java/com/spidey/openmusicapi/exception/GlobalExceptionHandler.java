package com.spidey.openmusicapi.exception;

import com.spidey.openmusicapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param ex 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 运行时异常处理
     * @param ex 运行时异常
     * @return 响应
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 自定义异常处理
     * @param ex 自定义异常
     * @return 响应
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(GlobalException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getCode(), ex.getMessage()), HttpStatus.valueOf(ex.getCode()));
    }

    /**
     * 参数校验异常处理
     * @param ex 参数校验异常
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        ex.getStatusCode().value(),
                        ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining("\n")),
                        ex.getBindingResult().getFieldErrors()),
                ex.getStatusCode());
    }
}