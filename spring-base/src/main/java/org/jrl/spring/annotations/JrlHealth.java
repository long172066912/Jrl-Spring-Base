package org.jrl.spring.annotations;

import java.lang.annotation.*;

/**
* 通过在bean方法上添加注解方式，实现健康检查
* @author JerryLong
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JrlHealth {

}