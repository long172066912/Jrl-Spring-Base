package org.jrl.spring.config;

import org.jrl.spring.JrlSpringBeanProcessor;
import org.jrl.spring.health.JrlHealthCheckHandler;
import org.jrl.spring.preheat.JrlPreHeatHealthCheckHandler;
import org.jrl.spring.scanner.JrlAnnotationBeanProcessor;
import org.jrl.spring.scanner.JrlBeanProcessor;
import org.jrl.spring.shutdown.JrlSpringShutdownListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
* 注册服务
* @author JerryLong
*/
@Configuration
public class JrlSpringConfig {
    @Bean
    @ConditionalOnMissingBean
    public JrlHealthCheckHandler jrlHealthCheckHandler() {
        return new JrlHealthCheckHandler(3);
    }

    @Bean
    @ConditionalOnMissingBean
    public JrlPreHeatHealthCheckHandler jrlPreHeatHealthCheckHandler() {
        return new JrlPreHeatHealthCheckHandler();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnMissingBean
    public JrlBeanProcessor jrlBeanProcessor(List<JrlSpringBeanProcessor> springBeanProcessors) {
        return new JrlBeanProcessor(springBeanProcessors);
    }

    @Bean
    @ConditionalOnMissingBean
    public JrlSpringShutdownListener jrlSpringShutdownListener() {
        return new JrlSpringShutdownListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public JrlAnnotationBeanProcessor jrlAnnotationBeanProcessor() {
        return new JrlAnnotationBeanProcessor();
    }
}
