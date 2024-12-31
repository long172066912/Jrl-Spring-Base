package org.jrl.spring.test.handler.preheat;

import org.jrl.spring.annotations.JrlPreheat;
import org.springframework.stereotype.Service;

@Service
public class JrlPreheatAnnotationTest1 {

    @JrlPreheat(order = 2)
    public void preheat() {
        System.out.println("JrlPreheatAnnotationTest1");
//        throw new RuntimeException("JrlPreheatAnnotationTest1");
    }
}
