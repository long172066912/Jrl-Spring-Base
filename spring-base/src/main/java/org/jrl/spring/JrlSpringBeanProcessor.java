package org.jrl.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.Ordered;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
* 类扫描处理接口
* @author JerryLong
*/
public interface JrlSpringBeanProcessor extends Ordered {
    /**
     * 前置处理-字段处理
     *
     * @param beanFactory
     * @param bean
     * @param beanName
     * @param field
     */
    default Object beforeInitializationField(BeanFactory beanFactory, Object bean, String beanName, Field field) throws Exception {
        return bean;
    }

    /**
     * 前置处理-方法
     *
     * @param beanFactory
     * @param bean
     * @param beanName
     * @param method
     */
    default Object beforeInitializationMethod(BeanFactory beanFactory, Object bean, String beanName, Method method) {
        return bean;
    }

    /**
     * 后置处理
     * @param bean
     * @param beanName
     * @return
     * @throws Exception
     */
    default Object postProcessAfterInitialization(Object bean, String beanName)  throws Exception {
        return bean;
    }

    /**
     * 排序
     *
     * @return
     */
    @Override
    default int getOrder() {
        return 0;
    }
}
