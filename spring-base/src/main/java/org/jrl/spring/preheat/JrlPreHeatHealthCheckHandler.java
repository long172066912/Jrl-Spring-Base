package org.jrl.spring.preheat;

import org.jrl.spring.JrlAnnotationBeanMethodManager;
import org.jrl.spring.JrlHealthCheck;
import org.jrl.spring.JrlPreheatSpringHandler;
import org.jrl.spring.model.JrlBeanMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* 预热统一处理的健康检查实现
* @author JerryLong
*/
public class JrlPreHeatHealthCheckHandler implements JrlHealthCheck, BeanFactoryAware {

    private Logger LOGGER = LoggerFactory.getLogger(JrlPreHeatHealthCheckHandler.class);

    @Autowired(required = false)
    private List<JrlPreheatSpringHandler> jrlPreheatSpringHandlers;
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
        if (hasHandler) {
            return;
        }
        hasHandler = true;
        try {
            final List<JrlBeanMethodInfo> beanMethods = JrlAnnotationBeanMethodManager.getBeanMethods(beanFactory, JrlAnnotationBeanMethodManager.PREHEAT_PRE);
            if (null != jrlPreheatSpringHandlers && jrlPreheatSpringHandlers.size() > 0) {
                beanMethods.addAll(jrlPreheatSpringHandlers.stream().map(e -> {
                    try {
                        return new JrlBeanMethodInfo(e, e.getClass().getName(), e.getClass().getMethod("preHandle"), e.order());
                    } catch (NoSuchMethodException ex) {
                        LOGGER.info("jrl-spring PreHeatHealthCheckHandler error ! bean : {}", e.getClass().getSimpleName(), ex);
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList()));
            }
            beanMethods.sort(Comparator.comparing(JrlBeanMethodInfo::getOrder).reversed());
            for (JrlBeanMethodInfo beanMethod : beanMethods) {
                LOGGER.info("jrl-spring preheat begin ! order : {} , bean : {} , method : {}", beanMethod.getOrder(), beanMethod.getBean().getClass().getSimpleName(), beanMethod.getMethod().getName());
                beanMethod.getMethod().invoke(beanMethod.getBean());
                LOGGER.info("jrl-spring preheat end ! order : {} , bean : {} , method : {}", beanMethod.getOrder(), beanMethod.getBean().getClass().getSimpleName(), beanMethod.getMethod().getName());
            }
            preHeatStatus = true;
        } catch (Exception e) {
            LOGGER.error("jrl-spring PreHeatHealthCheckHandler handler fail !", e);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
