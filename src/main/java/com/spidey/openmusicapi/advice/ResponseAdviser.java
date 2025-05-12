package com.spidey.openmusicapi.advice;

import com.spidey.openmusicapi.common.ApiResponse;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应的状态码
 */
@ControllerAdvice
public class ResponseAdviser implements ResponseBodyAdvice<ApiResponse<?>> {
    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public ApiResponse<?> beforeBodyWrite(
            ApiResponse<?> body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        if (body != null) {
            response.setStatusCode(HttpStatusCode.valueOf(body.getCode()));
        }
        return body;
    }
}
