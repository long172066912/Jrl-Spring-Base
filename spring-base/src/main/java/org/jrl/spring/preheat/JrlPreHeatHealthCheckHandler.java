package org.jrl.spring.preheat;

import org.jrl.spring.JrlHealthCheck;
import org.jrl.spring.JrlPreheatSpringHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* 预热健康检查处理器
* @author JerryLong
*/
public class JrlPreHeatHealthCheckHandler implements JrlHealthCheck, BeanFactoryAware {

    private Logger LOGGER = LoggerFactory.getLogger(JrlPreHeatHealthCheckHandler.class);

    @Autowired(required = false)
    private List<JrlPreheatSpringHandler> preheatSpringHandlerList;
    private BeanFactory beanFactory;

    private static boolean hasHandler = false;
    private static boolean preHeatStatus = false;

    @Override
    public boolean health() {
        return preHeatStatus;
    }

    /**
     * 执行预热逻辑
     */
    public void doPreheat() {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
