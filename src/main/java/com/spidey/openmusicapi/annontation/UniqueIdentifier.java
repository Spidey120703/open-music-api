package com.spidey.openmusicapi.annontation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spidey.openmusicapi.validator.UniqueIdentifierValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检测唯一标识符注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueIdentifierValidator.class)
public @interface UniqueIdentifier {

    /**
     * 错误信息
     * @return 错误信息
     */
    String message() default "";

    /**
     * 字段名
     * @return 字段名
     */
    String field();

    /**
     * Mapper类型
     * @return Mapper类型
     */
    Class<? extends BaseMapper<?>> mapper();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
