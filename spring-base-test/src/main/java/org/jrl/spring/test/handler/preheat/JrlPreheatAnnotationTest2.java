package org.jrl.spring.test.handler.preheat;

import org.jrl.spring.annotations.JrlPreheat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JrlPreheatAnnotationTest2 {

    @Transactional(rollbackFor = Exception.class)
    public void test() {

    }

    @JrlPreheat(order = 0)
    public void health1() {
        System.out.println("JrlPreheatAnnotationTest2");
    }
}
