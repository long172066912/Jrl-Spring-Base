package org.jrl.spring.test.handler;

import org.jrl.spring.JrlSpring;
import org.jrl.spring.test.service.JrlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: //TODO (用一句话描述该文件做什么)
* @author JerryLong
*/
@Component
public class JrlSpringCacheHandler implements JrlSpring {

    @Autowired
    private JrlTestService jrlTestService;

    private int i = 0;

    @Override
    public boolean health() {
        return i == 1 && jrlTestService.getMap().containsKey("cache") && TestAnnotationProcessor.AAAA.equals(jrlTestService.getTestField());
    }

    @Override
    public void preHandle() throws Exception {
        jrlTestService.preHeat("cache", "cache");
        i++;
    }

    @Override
    public void shutdown() {
        jrlTestService.shutdown("cache", "cache");
    }

    @Override
    public int order() {
        return 1;
    }
}
