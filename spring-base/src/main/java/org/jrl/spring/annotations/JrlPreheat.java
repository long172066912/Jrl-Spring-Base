package org.jrl.spring.annotations;


import org.jrl.spring.constant.JrlSpringOrderConstants;

import java.lang.annotation.*;

/**
* 通过在bean方法上添加注解方式，实现预热
* @author JerryLong
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JrlPreheat {
    /**
     * 执行顺序，越大优先执行
     * @return
     */
    int order() default JrlSpringOrderConstants.DEFAULT_MIDDLE;
}