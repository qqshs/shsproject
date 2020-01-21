package com.bytter.gateway.filter;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关健康检查过滤器，URL中输入相应目录，检查网关情况。
 */
public class HealthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
//        String path = RequestContext.getCurrentContext().getRequest().getRequestURI();
//        return path.equalsIgnoreCase(url())||path.toLowerCase().endsWith(url());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(200);
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(responseBody());
            ctx.setSendZuulResponse(false);
        }

        return null;
    }

    public String url(){
        return "/zuulHealth";
    }

    public String responseBody(){
        Map<String,Object> body = new HashMap<String, Object>();
        body.put("message", "网关运行正常。");
        body.put("code", 200);
        Gson gson = new Gson();
        return gson.toJson(body);
    }
}
