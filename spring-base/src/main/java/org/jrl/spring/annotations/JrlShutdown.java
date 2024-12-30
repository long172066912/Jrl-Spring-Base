package org.jrl.spring.annotations;


import org.jrl.spring.constant.JrlSpringOrderConstants;

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
     * 执行顺序，范围0 ~ 100，越大优先执行，RPC关闭 88，MQ-Consumer 87，MQ-Producer 5
     * @return
     */
    int order() default JrlSpringOrderConstants.DEFAULT_MIDDLE;
}