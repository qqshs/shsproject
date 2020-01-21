package com.bytter.gateway.filter;

import com.bytter.gateway.feign.CheckUserFeign;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证过滤器,验证请求方是否已经登录,如果没有登录拒绝该请求,并记录日志.
 * 已经登录,则正常路由转发.
 * @author shs
 */
@Slf4j
public class AuthFilter extends ZuulFilter {
    final List<String> excludeUrls = new ArrayList<String>();
    public AuthFilter(){
        excludeUrls.add("/businessBasic/userLogin/login");
        excludeUrls.add("/businessBasic/userLogin/loginOut");
        excludeUrls.add("/dtest/demoTest/qps");
    }

    @Autowired
    public CheckUserFeign checkUserFeign;
    //final String exitURL =
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -90;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("请求URL："+ctx.getRequest().getRequestURL());
        if(isCheckToken(ctx.getRequest().getRequestURL())){
            //验证用户是否登陆
            log.info("token 验证");
            String token = ctx.getRequest().getHeader("access_token");
            log.info(token);
            if(checkLoin(token)){
                log.info("token 验证通过");

            }else{
                log.info("token 验证不通过");
                Map<String,Object> body = new HashMap<String, Object>();
                body.put("msg", "未登录或是登录过期。");
                body.put("code", 1001);
                Gson gson = new Gson();
                ctx.setResponseStatusCode(200);
                ctx.getResponse().setCharacterEncoding("UTF-8");
                ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                ctx.setResponseBody(gson.toJson(body));
                ctx.setSendZuulResponse(false);
            }
        }
        return null;
    }

    /**
     * 验证是否已经登陆
     * @param token
     * @return
     */
    public boolean checkLoin(String token){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("access_token",token);
        Map<String,Object> param = checkUserFeign.checkToken(map);
        if("0".equals(param.get("state"))){
            return true;
        }else{
            return false;
        }

    }

    /**
     * @author shs
     * @param requestUrl 请求URL
     * @return 需要验证token的返回true 不需要验证token的返回false
     */
    public boolean isCheckToken(StringBuffer requestUrl){
        boolean returnFlag = true;
        for (String url : excludeUrls){
            if((requestUrl.indexOf(url)>0)){
                returnFlag = false;
                break;
            }

        }
        return returnFlag;
    }
}
