package com.spidey.openmusicapi.utils;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.exception.CustomException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UtilityClass
public class ControllerUtils {

    public static <T> T checkNull(T entity, String message) {
        if (entity == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, message);
        }
        return entity;
    }

    public static <T> T checkNull(T entity) {
        return checkNull(entity, "数据不存在");
    }

    /**
     * 更新预处理，确保id一致性
     *
     * @param entity DO实体
     * @param id 实体ID
     * @param message 错误信息
     * @return 处理后的实体对象
     * @param <T> 实体类型
     */
    public static <T> T updatePrepare(T entity, Long id, String message) {
        try {
            Method idGetter = entity.getClass().getMethod("getId");
            Method idSetter = entity.getClass().getMethod("setId", Long.class);
            if (idGetter.invoke(entity) == null) {
                idSetter.invoke(entity, id);
            } else if (((Long) idGetter.invoke(entity)).longValue() != id.longValue()) {
                throw new CustomException(message);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static <T> T updatePrepare(T entity, Long id) {
        return updatePrepare(entity, id, "ID不一致");
    }

    public static <T> ApiResponse<T> getSuccess(T data) {
        return ApiResponse.success("查询成功", data);
    }

    public static ApiResponse<Boolean> verifyCreateResult(Boolean result) {
        if (!result) {
            throw new CustomException("添加失败");
        }
        return ApiResponse.success("添加成功", true);
    }

    public static ApiResponse<Boolean> verifyUpdateResult(Boolean result) {
        if (!result) {
            throw new CustomException("更新失败");
        }
        return ApiResponse.success("更新成功", true);
    }

    public static ApiResponse<Boolean> verifyDeleteResult(Boolean result) {
        if (!result) {
            throw new CustomException("删除失败");
        }
        return ApiResponse.success("删除成功", true);
    }
}
