package org.jrl.spring.health;

/**
 * 健康检查后置处理
 *
 * @author JerryLong
 */
public interface JrlHealthPostPositionHandler {
    /**
     * 健康检查通过后的处理，需要做好密等
     */
    void handle();

    /**
     * 排序
     *
     * @return
     */
    default int order() {
        return 0;
    }
}
