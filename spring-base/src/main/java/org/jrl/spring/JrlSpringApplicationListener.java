package org.jrl.spring;

import org.jrl.spring.preheat.JrlPreHeatHealthCheckHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
* jrl springboot启动监听处理
* @author JerryLong
*/
@Configuration
public class JrlSpringApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private JrlPreHeatHealthCheckHandler preHeatHealthCheckHandler;
    @Autowired(required = false)
    private HealthEndpoint endpoint;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        preHeatHealthCheckHandler.doPreheat();
        if (null != endpoint) {
            endpoint.health();
        }
    }
}
