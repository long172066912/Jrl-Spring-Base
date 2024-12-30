package org.jrl.spring;

import org.springframework.beans.BeansException;

/**
* 实现BeansException
* @author JerryLong
*/
public class JrlBeansException extends BeansException {

    public JrlBeansException(String msg) {
        super(msg);
    }

    public JrlBeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
