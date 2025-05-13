package com.spidey.openmusicapi.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.exception.NotFoundException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Contract;

@UtilityClass
public class ControllerUtils {

    @Contract("_ -> new")
    @NonNull
    public static <T> T checkNull(T entity, String message) {
        if (entity == null) {
            throw new NotFoundException(message);
        }
        return entity;
    }

    @Contract("_ -> new")
    @NonNull
    public static <T> T checkNull(T entity) {
        return checkNull(entity, "数据不存在");
    }

    /**
     * 检查唯一标识符
     * @param service 服务
     * @param entity 实体
     * @param msg 提示消息
     * @param identifierGetter 获取唯一标识符的getter
     * @param keyGetter 获取主键的getter
     * @param <T> 实体类型
     */
    public static <T> void checkUniqueIdentifier(
            IService<T> service,
            T entity,
            String msg,
            @NonNull SFunction<T, String> identifierGetter,
            @NonNull SFunction<T, ?> keyGetter) {

        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(identifierGetter, identifierGetter.apply(entity));
        if (keyGetter.apply(entity) != null) {
            wrapper.ne(keyGetter, keyGetter.apply(entity));
        }

        if (service.getOne(wrapper) != null) {
            throw new BadRequestException(msg);
        }
    }

    @Contract("_ -> new")
    @NonNull
    public static <T> ApiResponse<T> getSuccess(T data) {
        return ApiResponse.success("查询成功", data);
    }

    @Contract("_ -> new")
    @NonNull
    public static ApiResponse<Boolean> verifyCreateResult(Boolean result) {
        if (!result) {
            throw new BadRequestException("添加失败");
        }
        return ApiResponse.created();
    }

    @NonNull
    public static ApiResponse<Boolean> verifyUpdateResult(Boolean result, String successMsg, String failMsg) {
        if (!result) {
            throw new BadRequestException(failMsg);
        }
        return ApiResponse.success(successMsg, true);
    }

    @NonNull
    public static ApiResponse<Boolean> verifyUpdateResult(Boolean result) {
        return verifyUpdateResult(result, "更新成功", "更新失败");
    }

    @NonNull
    public static ApiResponse<Boolean> verifyDeleteResult(Boolean result, String successMsg, String failMsg) {
        if (!result) {
            return ApiResponse.noContent(failMsg);
        }
        return ApiResponse.success(successMsg, true);
    }

    @NonNull
    public static ApiResponse<Boolean> verifyDeleteResult(Boolean result) {
        return verifyDeleteResult(result, "删除成功", "删除失败");
    }
}
