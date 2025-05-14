package com.spidey.openmusicapi.utils;

import com.google.common.base.CaseFormat;
import lombok.NonNull;
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
    @NonNull
    public static String camel2snake(@NonNull String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    /**
     * 将横线小驼峰转换成大驼峰
     * 例如：hello-world->HelloWorld
     * @param str 横线小驼峰
     * @return 大驼峰
     */
    @NonNull
    public static String kebab2Camel(@NonNull String str) {
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, str);
    }

    /**
     * 将横线小驼峰转换成大蛇形。</br>
     * 例如：hello-world->HELLO_WORLD
     *
     * @param str 转换前的横线小驼峰命名的字符串
     * @return 转换后的大蛇形命名的字符串
     */
    @NonNull
    public static String kebab2Snake(@NonNull String str) {
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, str);
    }

}
