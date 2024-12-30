package org.jrl.spring;


import org.jrl.spring.constant.JrlSpringOrderConstants;

/**
* 定义Spring接口
* @author JerryLong
*/
public interface JrlSpring extends JrlPreheatSpringHandler, JrlHealthCheck {
    /**
     * 优雅停机处理
     */
    void shutdown();

    @Override
    default boolean health() {
        return true;
    }

    /**
     * 执行顺序，范围0 ~ 100，越大优先执行，RPC关闭 88，MQ-Consumer 87，MQ-Producer 5
     * @return
     */
    @Override
    default int order() {
        return JrlSpringOrderConstants.DEFAULT_MIDDLE;
    }
}
