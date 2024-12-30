package org.jrl.spring.shutdown;

import org.jrl.spring.JrlAnnotationBeanMethodManager;
import org.jrl.spring.JrlSpring;
import org.jrl.spring.constant.JrlServerStatus;
import org.jrl.spring.model.JrlBeanMethodInfo;
import org.jrl.spring.preheat.JrlPreHeatHealthCheckHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* shutdown处理
* @author JerryLong
*/
public class JrlSpringShutdownListener implements ApplicationListener<ContextClosedEvent>, BeanFactoryAware {

    private Logger LOGGER = LoggerFactory.getLogger(JrlPreHeatHealthCheckHandler.class);

    @Autowired(required = false)
    private List<JrlSpring> jrlSpringList;
    private BeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        JrlServerStatus.setStatus(JrlServerStatus.SHUTDOWN);
        final List<JrlBeanMethodInfo> beanMethods = JrlAnnotationBeanMethodManager.getBeanMethods(beanFactory, JrlAnnotationBeanMethodManager.SHUTDOWN_PRE);
        if (null != jrlSpringList && jrlSpringList.size() > 0) {
            beanMethods.addAll(jrlSpringList.stream().map(e -> {
                try {
                    return new JrlBeanMethodInfo(e, e.getClass().getName(), e.getClass().getMethod("shutdown"), e.order());
                } catch (NoSuchMethodException ex) {
                    LOGGER.info("jrl-spring ShutdownListener error ! bean : {}", e.getClass().getSimpleName(), ex);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        beanMethods.sort(Comparator.comparing(JrlBeanMethodInfo::getOrder).reversed());
        for (JrlBeanMethodInfo beanMethod : beanMethods) {
            try {
                LOGGER.info("jrl-spring shutdown begin ! order : {} , bean : {} , method : {}", beanMethod.getOrder(), beanMethod.getBean().getClass().getSimpleName(), beanMethod.getMethod().getName());
                beanMethod.getMethod().invoke(beanMethod.getBean());
                LOGGER.info("jrl-spring shutdown end ! order : {} , bean : {} , method : {}", beanMethod.getOrder(), beanMethod.getBean().getClass().getSimpleName(), beanMethod.getMethod().getName());
            } catch (Throwable e) {
                LOGGER.error("jrl-spring shutdown error ! order : {} , bean : {} , method : {}", beanMethod.getBean().getClass().getSimpleName(), beanMethod.getMethod().getName(), e);
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
