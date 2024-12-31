package org.jrl.spring.test.handler.health;

import org.jrl.spring.annotations.JrlHealth;
import org.springframework.stereotype.Component;

@Component
public class JrlHealthAnnotationTest2 {

    @JrlHealth
    public boolean health1() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("JrlHealthAnnotationTest2");
        return true;
    }
}
