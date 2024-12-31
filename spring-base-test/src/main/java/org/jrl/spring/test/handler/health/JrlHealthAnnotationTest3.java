package org.jrl.spring.test.handler.health;

import org.jrl.spring.annotations.JrlHealth;
import org.springframework.stereotype.Component;

@Component
public class JrlHealthAnnotationTest3 {

    @JrlHealth
    public void health1() {
        System.out.println("JrlHealthAnnotationTest3");
//        throw new RuntimeException("test");
    }
}
