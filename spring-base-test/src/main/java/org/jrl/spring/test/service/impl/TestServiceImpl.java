package org.jrl.spring.test.service.impl;

import org.jrl.spring.test.annotations.JrlTestAnnotation;
import org.jrl.spring.test.service.JrlTestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements JrlTestService {

    public Map<String, String> map = new HashMap<>();

    @JrlTestAnnotation
    private String testField;

    @Override
    public boolean health(String k, String v) {
        return map.get(k).equals(v);
    }

    @Override
    public void preHeat(String k, String v) {
        map.put(k, v);
    }

    @Override
    public void shutdown(String k, String v) {
        map.remove(k, v);
    }

    @Override
    public void test() {
        map.put(testField, testField);
    }

    @Override
    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public String getTestField() {
        return testField;
    }
}
