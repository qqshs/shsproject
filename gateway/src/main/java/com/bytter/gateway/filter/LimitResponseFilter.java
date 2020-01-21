/**
 * 改写限流后返回的状态码
 */
package com.bytter.gateway.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author shs
 * @deprecated
 */
public class LimitResponseFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		if(ctx.getResponseStatusCode()==429) {
			System.out.println("请求过快 稍后再试");
			Map<String,Object> body = new HashMap<String, Object>();
			body.put("message", "请求过快 稍后再试");
			body.put("code", 200);
			Gson gson = new Gson();
			ctx.setResponseStatusCode(200);
			ctx.getResponse().setCharacterEncoding("UTF-8");
			ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			ctx.setResponseBody(gson.toJson(body));
		}
		return null;
	}

	@Override
	public String filterType() {
		
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		
		return 30;
	}

}
