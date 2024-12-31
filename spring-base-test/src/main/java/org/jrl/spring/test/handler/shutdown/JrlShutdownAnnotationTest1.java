package org.jrl.spring.test.handler.shutdown;

import org.jrl.spring.annotations.JrlShutdown;
import org.springframework.stereotype.Service;

@Service
public class JrlShutdownAnnotationTest1 {

    public boolean status = false;

    public void start() {
        status = true;
    }

    public void stop() {
        status = false;
    }

    @JrlShutdown(order = 2)
    public void health1() {
        System.out.println("JrlShutdownAnnotationTest1 start !");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("JrlShutdownAnnotationTest1 end !");
    }
}
