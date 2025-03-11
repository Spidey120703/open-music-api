package com.spidey.openmusicapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success("操作成功", data);
    }

    public static ApiResponse<?> success() {
        return ApiResponse.success(null);
    }

    public static ApiResponse<?> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static ApiResponse<?> error(String message) {
        return ApiResponse.error(500, message);
    }

    public static ApiResponse<?> error() {
        return ApiResponse.error("操作失败");
    }
}