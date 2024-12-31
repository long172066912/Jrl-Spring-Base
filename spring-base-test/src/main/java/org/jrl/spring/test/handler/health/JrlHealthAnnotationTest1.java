package org.jrl.spring.test.handler.health;

import org.jrl.spring.annotations.JrlHealth;
import org.springframework.stereotype.Service;

@Service
public class JrlHealthAnnotationTest1 {

    public boolean status = false;

    public void start() {
        status = true;
    }

    public void stop() {
        status = false;
    }

    @JrlHealth
    public Boolean health1() {
        System.out.println("JrlHealthAnnotationTest1!");
        return status;
    }
}
