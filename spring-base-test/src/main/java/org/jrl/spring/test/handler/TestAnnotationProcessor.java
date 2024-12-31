package org.jrl.spring.test.handler;

import org.jrl.spring.JrlSpringBeanProcessor;
import org.jrl.spring.test.annotations.JrlTestAnnotation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class TestAnnotationProcessor implements JrlSpringBeanProcessor {

    public static final String AAAA = "aaaaa";

    @Override
    public Object beforeInitializationField(BeanFactory beanFactory, Object bean, String beanName, Field field) throws Exception {
        if (field.getAnnotation(JrlTestAnnotation.class) != null) {
            field.setAccessible(true);
            field.set(bean, AAAA);
        }
        return bean;
    }
}
