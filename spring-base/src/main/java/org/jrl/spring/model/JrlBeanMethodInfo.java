package org.jrl.spring.model;


import java.io.Serializable;
import java.lang.reflect.Method;

/**
* bean实体对象
* @author JerryLong
*/
public class JrlBeanMethodInfo implements Serializable {
    private Object bean;
    private String beanName;
    private Method method;
    private Integer order;

    public JrlBeanMethodInfo() {
    }

    public JrlBeanMethodInfo(String beanName, Method method, Integer order) {
        this.beanName = beanName;
        this.method = method;
        this.order = order;
    }

    public JrlBeanMethodInfo(Object bean, String beanName, Method method, Integer order) {
        this.bean = bean;
        this.beanName = beanName;
        this.method = method;
        this.order = order;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
