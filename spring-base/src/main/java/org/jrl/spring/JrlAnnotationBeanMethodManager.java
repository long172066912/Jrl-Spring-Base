package org.jrl.spring;

import org.jrl.spring.model.JrlBeanMethodInfo;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
* 注解扫描管理类
* @author JerryLong
*/
public class JrlAnnotationBeanMethodManager {
    private static Map<String, JrlBeanMethodInfo> beanMethodMap = new ConcurrentHashMap<>();

    public static final String HEALTH_PRE = "JrlHealth_";
    public static final String PREHEAT_PRE = "JrlPreheat_";
    public static final String SHUTDOWN_PRE = "JrlShutdown_";

    public static void registBean(String name, String beanName, Method method, int order) {
        beanMethodMap.putIfAbsent(name, new JrlBeanMethodInfo(beanName, method, order));
    }

    public static List<JrlBeanMethodInfo> getBeanMethods(BeanFactory beanFactory, String pre) {
        if (beanMethodMap.size() == 0) {
            return new ArrayList<>();
        }
        return beanMethodMap.entrySet().stream().filter(entry -> entry.getKey().startsWith(pre)).map(e -> {
            final JrlBeanMethodInfo methodInfo = e.getValue();
            if (null == methodInfo.getBean()) {
                methodInfo.setBean(beanFactory.getBean(e.getValue().getBeanName()));
            }
            return methodInfo;
        }).sorted(Comparator.comparing(JrlBeanMethodInfo::getOrder)).collect(Collectors.toList());
    }
}
