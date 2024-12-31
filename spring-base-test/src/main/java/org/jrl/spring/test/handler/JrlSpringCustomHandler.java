package org.jrl.spring.test.handler;

import org.jrl.spring.JrlSpring;
import org.jrl.spring.health.JrlHealthPostPositionHandler;
import org.jrl.spring.test.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: //TODO (用一句话描述该文件做什么)
* @author JerryLong
*/
@Component
public class JrlSpringCustomHandler implements JrlSpring, JrlHealthPostPositionHandler {

    @Autowired
    private TestServiceImpl testService;

    private int i = 0;

    @Override
    public boolean health() {
        return i == 1 && testService.map.containsKey("custom");
    }

    @Override
    public void preHandle() {
        testService.preHeat("custom", "custom");
        i++;
    }

    @Override
    public void shutdown() {
        testService.shutdown("custom", "custom");
    }

    @Override
    public void handle() {
        System.out.println("健康检查成功！");
//        throw new RuntimeException("aaa");
    }

    @Override
    public int order() {
        return 3;
    }
}
