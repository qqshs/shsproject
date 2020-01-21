package com.bytter.gateway.feign.fallBack;

import com.bytter.gateway.feign.CheckUserFeign;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class checkUserFeignFallBack implements CheckUserFeign {


    @Override
    public Map<String, Object> checkToken(Map<String, Object> map) {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("state", "-1");
        return returnMap;

    }
}
