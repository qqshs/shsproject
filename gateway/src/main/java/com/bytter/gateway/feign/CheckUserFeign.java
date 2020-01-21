package com.bytter.gateway.feign;

import com.bytter.gateway.feign.fallBack.checkUserFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "businessBasic",fallback= checkUserFeignFallBack.class)
public interface CheckUserFeign {
    @RequestMapping(value = "/businessBasic/loginserver/v1.0.0/checkLogin",method = RequestMethod.POST)
    public Map<String,Object>checkToken(Map<String,Object> map);
}
