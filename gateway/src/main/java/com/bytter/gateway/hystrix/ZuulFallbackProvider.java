/**
 * zuul熔断器
 */
package com.bytter.gateway.hystrix;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;


/**
 * 熔断器
 * @author shs
 *
 */
@Component
public class ZuulFallbackProvider implements FallbackProvider{
	protected Logger log = LoggerFactory.getLogger(ZuulFallbackProvider.class.getName());
	/**
	 * 指定有熔断需求的路由，为星号时代表全部配置的路由。
	 */
	public String getRoute() {
        return "*";
    }
    public ClientHttpResponse fallbackResponse(String route, Throwable throwable) {
        if (throwable != null) {
            //throwable.printStackTrace();
            log.error("Excption {}", throwable);
        }
    	
        return new ClientHttpResponse() {

            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            public int getRawStatusCode() throws IOException {
                return 200;
            }

            public String getStatusText() throws IOException {
                return "OK";
            }

            public void close() {

            }

            public InputStream getBody() throws IOException {
            	log.error("zuul false");
            	log.info("gateway false");
            	Gson gson = new Gson();
            	Map<String, Object> map = new HashMap<String, Object>(); 
            	map.put("message", "调用业务功能失败");
            	map.put("code", "200");
                return new ByteArrayInputStream(gson.toJson(map).getBytes("UTF-8"));
            }

            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
