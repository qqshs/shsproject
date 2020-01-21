/**
 * 限流过滤器
 */
package com.bytter.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author shs
 *
 */
@Slf4j
public class LimitFilter extends ZuulFilter{
	final RateLimiter rateLimiter = RateLimiter.create(60.0);
	final String exitURL = "/demoTest/qps";

	@Override
	public boolean shouldFilter() {
		
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		try {

			RequestContext ctx = RequestContext.getCurrentContext();
			log.info("限流过滤器");
			if(!rateLimiter.tryAcquire()) {
				Map<String,Object> body = new HashMap<String, Object>();
				log.info("请求过快 稍后再试");
				body.put("message", "请求过快 稍后再试");
				body.put("code", 200);
				TimeUnit.SECONDS.sleep(1);
				Gson gson = new Gson();
				ctx.setResponseStatusCode(200);
				ctx.getResponse().setCharacterEncoding("UTF-8");
				ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				ctx.setResponseBody(gson.toJson(body));
				ctx.setSendZuulResponse(false);
			} 
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		
		return null;
	}

	@Override
	public String filterType() {
		
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		
		return -100;
	}

}
