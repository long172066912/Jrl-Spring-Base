package org.jrl.spring.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

/**
* 类帮助类
* @author JerryLong
*/
public class JrlClassUtil {

    /**
     * 获取父类泛型
     *
     * @param clazz
     * @return
     */
    public static Type getGenericSuperclass(Class clazz) {
        //获取泛型父类
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * 获取类所有字段列表（不包括合成字段）
     *
     * @param clazz
     * @return
     */
    public static Set<Field> getDeclaredFieldSets(Class<?> clazz) {
        return stream(clazz.getDeclaredFields()).filter(JrlClassUtil::classDeclaredFieldFilter).collect(toCollection(LinkedHashSet::new));
    }

    /**
     * 获取类所有字段列表（不包括合成字段）
     *
     * @param clazz
     * @return
     */
    public static Field[] getDeclaredFields(Class<?> clazz) {
        return stream(clazz.getDeclaredFields()).filter(JrlClassUtil::classDeclaredFieldFilter).toArray(Field[]::new);
    }

    private static boolean classDeclaredFieldFilter(Field field) {
        return !(field.isSynthetic());
    }
}
