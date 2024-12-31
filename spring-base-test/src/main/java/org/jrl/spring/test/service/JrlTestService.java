package org.jrl.spring.test.service;

import java.util.Map;

/**
* @Description: //TODO (用一句话描述该文件做什么)
* @author JerryLong
*/
public interface JrlTestService {

    boolean health(String k, String v);

    void preHeat(String k, String v);

    void shutdown(String k, String v);

    void test();

    Map<String, String> getMap();

    String getTestField();
}
