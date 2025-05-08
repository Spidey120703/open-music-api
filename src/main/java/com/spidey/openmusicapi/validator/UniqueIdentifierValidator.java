package com.spidey.openmusicapi.validator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spidey.openmusicapi.annontation.UniqueIdentifier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 唯一标识符验证器
 */
@Component
@RequiredArgsConstructor
public class UniqueIdentifierValidator
        implements ConstraintValidator<UniqueIdentifier, String> {

    /**
     * Spring上下文
     */
    private final ApplicationContext applicationContext;

    /**
     * Mapper映射实例
     */
    private BaseMapper<?> mapper;

    /**
     * 字段名
     */
    private String field;

    @Override
    public void initialize(UniqueIdentifier constraintAnnotation) {
        // 获取Mapper映射实体
        this.mapper = applicationContext.getBean(constraintAnnotation.mapper());
        this.field = constraintAnnotation.field();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 获取查询条件
        QueryWrapper<Object> wrapper = new QueryWrapper<>().eq(this.field, s);
        // 查询
        BaseMapper<Object> typedMapper = (BaseMapper<Object>) this.mapper; // 强制类型转换
        return typedMapper.selectOne(wrapper) == null;
    }

}
