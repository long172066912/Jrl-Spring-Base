package org.jrl.spring.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
* 健康检查
* @author JerryLong
*/
public class JrlHealthCheckHandler extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

    }
}