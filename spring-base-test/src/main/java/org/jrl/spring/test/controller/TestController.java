package org.jrl.spring.test.controller;

import org.jrl.spring.test.handler.health.JrlHealthAnnotationTest1;
import org.jrl.spring.test.handler.health.JrlHealthAnnotationTest3;
import org.jrl.spring.test.service.JrlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: //TODO (用一句话描述该文件做什么)
* @author JerryLong
*/
@RestController
public class TestController {

    @Autowired
    private JrlTestService jrlTestService;
    @Autowired
    private JrlHealthAnnotationTest1 jrlHealthAnnotationTest1;
    @Autowired
    private JrlHealthAnnotationTest3 jrlHealthAnnotationTest3;

    @PostMapping("/test")
    public Map<String, String> test() {
        jrlTestService.test();
        return jrlTestService.getMap().entrySet().stream().filter(e -> null != e.getValue()).collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getValue));
    }

    @PostMapping("/start")
    public void start() {
        jrlHealthAnnotationTest1.start();
    }

    @PostMapping("/stop")
    public void stop() {
        jrlHealthAnnotationTest1.stop();
    }

    @PostMapping("/test1")
    public void test1() {
        jrlHealthAnnotationTest1.health1();
        jrlHealthAnnotationTest3.health1();
    }
}
