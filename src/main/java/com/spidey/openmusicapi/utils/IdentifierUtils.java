package com.spidey.openmusicapi.utils;

import com.google.common.base.CaseFormat;
import lombok.experimental.UtilityClass;

/**
 * 命名工具类
 *
 * @author Spidey
 */
@UtilityClass
public class IdentifierUtils {

    /**
     * 将小驼峰转换成小蛇形。</br>
     * 例如：helloWorld->hello_world
     *
     * @param str 转换前的小驼峰命名的字符串
     * @return 转换后的小蛇形命名的字符串
     */
    public static String toSnakeCase(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

}
