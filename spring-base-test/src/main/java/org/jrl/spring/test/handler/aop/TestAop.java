package org.jrl.spring.test.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAop {

    @Pointcut("within(org.jrl.spring.test.handler.health.*)")
    public void test() {

    }

    @Around("test()")
    public Object testAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("我是AOP : " + pjp.getTarget().getClass().getName());
        return pjp.proceed();
    }
}
