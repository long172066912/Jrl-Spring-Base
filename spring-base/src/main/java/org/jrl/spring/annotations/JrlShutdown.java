package org.jrl.spring.annotations;

import java.lang.annotation.*;

/**
* 通过在bean方法上添加注解方式，实现优雅停机
* @author JerryLong
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JrlShutdown {
    /**
     * 执行顺序
     * @return
     */
    int order() default 0;
}