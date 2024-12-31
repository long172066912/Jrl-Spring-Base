package org.jrl.spring.test.annotations;

import java.lang.annotation.*;

/**
* @Description: //TODO (用一句话描述该文件做什么)
* @author JerryLong
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface JrlTestAnnotation {

}