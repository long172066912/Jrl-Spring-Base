package org.jrl.spring.scanner;

import org.jrl.spring.JrlAnnotationBeanMethodManager;
import org.jrl.spring.JrlSpringBeanProcessor;
import org.jrl.spring.annotations.JrlHealth;
import org.jrl.spring.annotations.JrlPreheat;
import org.jrl.spring.annotations.JrlShutdown;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;

/**
* 注解扫描器
* @author JerryLong
*/
public class JrlAnnotationBeanProcessor implements JrlSpringBeanProcessor {

    @Override
    public Object beforeInitializationMethod(BeanFactory beanFactory, Object bean, String beanName, Method method) {
        health(method.getAnnotation(JrlHealth.class), beanName, bean, method);
        preheat(method.getAnnotation(JrlPreheat.class), beanName, bean, method);
        shutdown(method.getAnnotation(JrlShutdown.class), beanName, bean, method);
        return bean;
    }

    private void health(JrlHealth health, String beanName, Object bean, Method method) {
        if (null != health) {
            JrlAnnotationBeanMethodManager.registBean(JrlAnnotationBeanMethodManager.HEALTH_PRE + beanName, beanName, method, 0);
        }
    }

    private void preheat(JrlPreheat preheat, String beanName, Object bean, Method method) {
        if (null != preheat) {
            JrlAnnotationBeanMethodManager.registBean(JrlAnnotationBeanMethodManager.PREHEAT_PRE + beanName, beanName, method, preheat.order());
        }
    }

    private void shutdown(JrlShutdown shutdown, String beanName, Object bean, Method method) {
        if (null != shutdown) {
            JrlAnnotationBeanMethodManager.registBean(JrlAnnotationBeanMethodManager.SHUTDOWN_PRE + beanName, beanName, method, shutdown.order());
        }
    }
}