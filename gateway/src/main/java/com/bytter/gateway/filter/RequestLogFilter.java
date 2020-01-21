/**
 * 访问日志，记录用户、第三方服务调用访问系统的日志。
 * 统计访问量、交易数据、接口调用成功率。
 */
package com.bytter.gateway.filter;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author shs
 *
 */
public class RequestLogFilter extends ZuulFilter{
	protected Logger log = LoggerFactory.getLogger(RequestLogFilter.class.getName());
	final RateLimiter rateLimiter = RateLimiter.create(20.0);

	@Override
	public boolean shouldFilter() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		
		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			InputStream in = request.getInputStream();
			
			String reqBbody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			// 打印userId，获取其他用户信息
			if (reqBbody != null) {
				log.info("request");
				/*JSONObject json = JSONObject.fromObject(reqBbody);
				Object userId = json.get("userId");
				if (userId != null) {
					PrintRequestLogFilter.LOGGER.info("request userId:\t" + userId);
                }*/
			}
			// 打印请求方法，路径
			log.info("request url:\t" + request.getMethod() + "\t" + request.getRequestURL().toString());
			/* Map<String, String[]> map = request.getParameterMap(); */
			// 打印请求url参数
			/*if (map != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("request parameters:\t");
				for (Map.Entry<String, String[]> entry : map.entrySet()) {
					sb.append("[" + entry.getKey() + "=" + printArray(entry.getValue()) + "]");
				}
				log.info(sb.toString());
			}*/
			// 打印请求json参数
			if (reqBbody != null) {
				log.info("request body:\t" + reqBbody);
			}
 
			// 打印response
			InputStream out = ctx.getResponseDataStream();
			String outBody = StreamUtils.copyToString(out, Charset.forName("UTF-8"));
			if (outBody != null) {
				log.info("response body:\t" + outBody);
			}
			if(!rateLimiter.tryAcquire()) {
				log.info("no token");
				
				ctx.setResponseBody("{\"message\":\"请求过快\",\"responseCode\":204}");
				ctx.setResponseStatusCode(200);
				ctx.getResponse().setCharacterEncoding("UTF-8");
				ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				ctx.setSendZuulResponse(false);
			}else {
				ctx.setResponseBody("{\"message\":\"falseddddd\"}");//重要
			}
 
			
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String filterType() {
		// TODO 自动生成的方法存根
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		// 过滤器优先级0，数字越小优先级越高。
		return -3;
	}

}
