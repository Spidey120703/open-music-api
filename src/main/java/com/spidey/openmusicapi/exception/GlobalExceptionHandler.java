package com.spidey.openmusicapi.exception;

import com.spidey.openmusicapi.common.ApiResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    public ResponseEntity<ApiResponse<?>> handleException(@NonNull Exception ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 运行时异常处理
     * @param ex 运行时异常
     * @return 响应
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(@NonNull RuntimeException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 自定义异常处理
     * @param ex 自定义异常
     * @return 响应
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(@NonNull GlobalException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getCode(), ex.getMessage()), HttpStatus.valueOf(ex.getCode()));
    }

    /**
     * 参数校验异常处理
     * @param ex 参数校验异常
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
            @NonNull MethodArgumentNotValidException ex) {
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

    /**
     * 用户名不存在异常处理
     * @param ex 用户名不存在异常
     * @return 响应
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFoundException(@NonNull UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * 权限不足异常处理
     * @param ex 权限不足异常
     * @return 响应
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(@NonNull AuthorizationDeniedException ignored) {
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.FORBIDDEN.value(), "暂无权限"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(@NonNull HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "请求体格式错误"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(@NonNull HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持 '%s' 请求".formatted(ex.getMethod())),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

}