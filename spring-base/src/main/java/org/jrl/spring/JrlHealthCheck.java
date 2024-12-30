package org.jrl.spring;

/**
* 健康检查接口
* @author JerryLong
*/
@FunctionalInterface
public interface JrlHealthCheck {
    /**
     * 健康检查
     *
     * @return
     */
    boolean health();
}
