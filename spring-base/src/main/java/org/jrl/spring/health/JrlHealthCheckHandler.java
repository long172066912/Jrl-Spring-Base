package org.jrl.spring.health;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.jrl.spring.JrlAnnotationBeanMethodManager;
import org.jrl.spring.JrlHealthCheck;
import org.jrl.spring.constant.JrlServerStatus;
import org.jrl.spring.model.JrlBeanMethodInfo;
import org.jrl.spring.preheat.JrlPreHeatHealthCheckHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
* 实现spring-actuator健康检查
* @author JerryLong
*/
public class JrlHealthCheckHandler extends AbstractHealthIndicator implements BeanFactoryAware {

    private Logger LOGGER = LoggerFactory.getLogger(JrlPreHeatHealthCheckHandler.class);
    /**
     * 失败次数到了就打errorlog
     */
    private final int failLogNum;
    private int failNum = 0;

    @Autowired(required = false)
    private List<JrlHealthCheck> jrlHealthChecks;
    @Autowired(required = false)
    private List<JrlHealthPostPositionHandler> postPositionHandlers;
    private BeanFactory beanFactory;

    private static final int HEALTH_TIME = 200;
    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new NamedThreadFactory("JrlSpringBaseHealthCheck"), new ThreadPoolExecutor.CallerRunsPolicy());

    public JrlHealthCheckHandler(int failLogNum) {
        this.failLogNum = failLogNum;
    }

    @Override
    protected void doHealthCheck(Health.Builder bldr) throws Exception {
        boolean health = true;
        if (null != jrlHealthChecks && jrlHealthChecks.size() > 0) {
            for (JrlHealthCheck jrlHealthCheck : jrlHealthChecks) {
                final boolean h = jrlHealthCheck.health();
                if (!h) {
                    LOGGER.warn("jrl-spring doHealthCheck checker : {} , health : {}", jrlHealthCheck.getClass().getSimpleName(), h);
                    health = false;
                    break;
                }
            }
        }
        if (health) {
            final List<JrlBeanMethodInfo> beanMethods = JrlAnnotationBeanMethodManager.getBeanMethods(beanFactory, JrlAnnotationBeanMethodManager.HEALTH_PRE);
            for (JrlBeanMethodInfo beanMethod : beanMethods) {
                if (!health) {
                    break;
                }
                try {
                    final Future<Object> submit = executorService.submit(() -> beanMethod.getMethod().invoke(beanMethod.getBean()));
                    Object result = null;
                    try {
                        result = submit.get(HEALTH_TIME, TimeUnit.MILLISECONDS);
                    } catch (TimeoutException e) {
                        LOGGER.error("jrl-spring doHealthCheck timeout ! more than : {} ! checker : {} , health : {}", HEALTH_TIME, beanMethod.getBean().getClass().getSimpleName(), health);
                        //超时不处理health状态
                        continue;
                    }
                    if (boolean.class.isAssignableFrom(beanMethod.getMethod().getReturnType()) || Boolean.class.isAssignableFrom(beanMethod.getMethod().getReturnType())) {
                        health = null != result && (boolean) result;
                    } else if (Void.class.isAssignableFrom(beanMethod.getMethod().getReturnType())) {
                        health = true;
                    } else {
                        health = null == result;
                    }
                    if (!health) {
                        LOGGER.warn("jrl-spring doHealthCheck fail ! checker : {} , health : {}", beanMethod.getBean().getClass().getSimpleName(), health);
                    }
                } catch (Throwable e) {
                    health = false;
                    LOGGER.error("jrl-spring doHealthCheck error ! checker : {} , health : {}", beanMethod.getBean().getClass().getSimpleName(), health, e);
                }
            }
        }
        if (health) {
            if (null != postPositionHandlers && postPositionHandlers.size() > 0) {
                postPositionHandlers.stream().sorted(Comparator.comparing(JrlHealthPostPositionHandler::order)).forEach(handle -> {
                    LOGGER.info("jrl-spring health doPostPositionHandler begin ! handler : {}", handle.getClass().getSimpleName());
                    handle.handle();
                    LOGGER.info("jrl-spring health doPostPositionHandler end ! handler : {}", handle.getClass().getSimpleName());
                });
            }
            JrlServerStatus.setStatus(JrlServerStatus.RUNNING);
            LOGGER.info("jrl-spring doHealthCheck success !");
            bldr.up();
        } else {
            if (failNum >= failLogNum) {
                failNum = 0;
                LOGGER.error("jrl-spring doHealthCheck fail !");
            } else {
                failNum++;
                LOGGER.warn("jrl-spring doHealthCheck fail !");
            }
            JrlServerStatus.setStatus(JrlServerStatus.ABNORMAL);
            bldr.down();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}