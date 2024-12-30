package org.jrl.spring.scanner;

import org.jrl.spring.JrlBeansException;
import org.jrl.spring.JrlSpringBeanProcessor;
import org.jrl.spring.utils.JrlClassUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
* 覆盖cache组件中的后置处理器
* @author JerryLong
*/
public class JrlBeanProcessor implements BeanPostProcessor, BeanFactoryAware, PriorityOrdered {

    public JrlBeanProcessor(List<JrlSpringBeanProcessor> jrlSpringBeanProcessors) {
        this.jrlSpringBeanProcessors = jrlSpringBeanProcessors;
    }

    private final List<JrlSpringBeanProcessor> jrlSpringBeanProcessors;

    private BeanFactory beanFactory;

    /**
     * todo 支持外部扩展类路径限制
     */
    private static final String COM_PREFIX = "com";


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (null == jrlSpringBeanProcessors || jrlSpringBeanProcessors.size() == 0) {
            return bean;
        }
        for (JrlSpringBeanProcessor jrlSpringBeanProcessor : jrlSpringBeanProcessors) {
            try {
                bean = jrlSpringBeanProcessor.postProcessAfterInitialization(bean, beanName);
            } catch (Exception e) {
                throw new JrlBeansException(e.getMessage(), e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!checkPath(bean.getClass())) {
            return bean;
        }
        if (null == jrlSpringBeanProcessors || jrlSpringBeanProcessors.size() == 0) {
            return bean;
        }
        Class<?> cls = bean.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (!cls.equals(Object.class)) {
            fieldList.addAll(JrlClassUtil.getDeclaredFieldSets(cls));
            cls = cls.getSuperclass();
        }
        for (Field field : fieldList) {
            for (JrlSpringBeanProcessor jrlSpringBeanProcessor : jrlSpringBeanProcessors) {
                try {
                    jrlSpringBeanProcessor.beforeInitializationField(beanFactory, bean, beanName, field);
                } catch (Exception e) {
                    throw new JrlBeansException(e.getMessage(), e);
                }
            }
        }
        final Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            for (JrlSpringBeanProcessor jrlSpringBeanProcessor : jrlSpringBeanProcessors) {
                bean = jrlSpringBeanProcessor.beforeInitializationMethod(beanFactory, bean, beanName, method);
            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private boolean checkPath(Class clazz) {
        if (clazz.getName().startsWith(COM_PREFIX)) {
            return true;
        }
        return false;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}