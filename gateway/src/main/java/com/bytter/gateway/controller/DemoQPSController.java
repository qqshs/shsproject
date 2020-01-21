package com.bytter.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author shs
 * QPS请求数测试类
 */
@RestController
@RequestMapping(value="/democlient")
@Slf4j
public class DemoQPSController {
    @RequestMapping(value="/qbs", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Map<String,Object> qps() {
        try {
            System.out.println("controller sleep 2s");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
