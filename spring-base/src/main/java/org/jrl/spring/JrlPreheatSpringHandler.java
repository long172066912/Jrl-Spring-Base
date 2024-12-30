package org.jrl.spring;


import org.jrl.spring.constant.JrlSpringOrderConstants;

/**
* 预热处理
* @author JerryLong
*/
@FunctionalInterface
public interface JrlPreheatSpringHandler {
    /**
     * 处理预热逻辑
     *
     * @return
     */
    void preHandle() throws Exception;

    /**
     * 执行顺序，范围0 ~ 100，越大优先执行
     * @return
     */
    default int order() {
        return JrlSpringOrderConstants.DEFAULT_MIDDLE;
    }
}
