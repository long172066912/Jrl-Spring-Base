package org.jrl.spring.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 常量定义
* @author JerryLong
*/
public enum JrlServerStatus {
    /**
     * 准备阶段
     */
    PREPARE,
    /**
     * 运行中
     */
    RUNNING,
    /**
     * 停止运行
     */
    SHUTDOWN,
    /**
     * 异常
     */
    ABNORMAL;

    private static Logger LOGGER = LoggerFactory.getLogger(JrlServerStatus.class);

    private static JrlServerStatus jrlServerStatus = PREPARE;

    public static void setStatus(JrlServerStatus status) {
        if (null == status) {
            return;
        }
        if (!jrlServerStatus.equals(status)) {
            LOGGER.info("JrlServerStatus oldStatus : {} , newStatus : {}", jrlServerStatus, status);
            jrlServerStatus = status;
        }
    }

    public static JrlServerStatus getStatus() {
        return jrlServerStatus;
    }
}
