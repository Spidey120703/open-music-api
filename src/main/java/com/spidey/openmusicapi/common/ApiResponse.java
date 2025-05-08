package com.spidey.openmusicapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Contract;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success("操作成功", data);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<?> success() {
        return ApiResponse.success(null);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<?> error(int code, String message, Object data) {
        return new ApiResponse<>(code, message, data);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<?> error(int code, String message) {
        return ApiResponse.error(code, message, null);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<?> error(String message) {
        return ApiResponse.error(500, message);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<?> error() {
        return ApiResponse.error("操作失败");
    }

    /*
     * 定制响应内容
     */

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(201, message, data);
    }

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.created("添加成功", data);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<Boolean> created() {
        return ApiResponse.created(true);
    }

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> noContent(String message, T data) {
        return new ApiResponse<>(204, message, data);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<Boolean> noContent(String message) {
        return ApiResponse.noContent(message, false);
    }
}