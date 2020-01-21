/**
 * zuul网关
 */
package com.bytter.gateway.boot;

import com.bytter.gateway.filter.*;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author shs
 *
 */
@EnableAutoConfiguration
@EnableZuulProxy
@ComponentScan(basePackages = {"com.bytter"})
@EnableEurekaClient
@Configuration
@EnableApolloConfig
@EnableFeignClients(basePackages = {"com.bytter.*"})
public class ZuulBoot implements WebMvcConfigurer{
	protected Logger log = LoggerFactory.getLogger(ZuulBoot.class.getName());
	static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };
	public static void main(String[] args) {
		
		SpringApplication.run(ZuulBoot.class, args);
    }
	
	/**
	 *
	 * 全域跨域访问
	 */
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*")
                .allowCredentials(true).allowedMethods(ORIGINS).maxAge(3600);
    }
    /**
     *     开启访问日志打印过滤器
     * @return
     */
    @Bean
    public RequestLogFilter requestLogFilter() {
    	log.info("requestLogFilter start..........");
    	return new RequestLogFilter();
    }
    /**
     * 限流过滤器
     * @return
     */
    @Bean
    public LimitFilter limitFilter() {
    	
    	return new LimitFilter();
    }
    @Bean
    public LimitResponseFilter limitResponseFilter() {
    	return new LimitResponseFilter();
    }

    /**
     * 开启监控过滤器
     * @return
     */
    @Bean
    public MonitorFilter monitorFilter(){
        return new MonitorFilter();
    }

    /**
     * 开启登录检查过滤器
     * @return
     */
    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }

    /**
     * 开启网关健康检查过滤器
     * @return
     */
    @Bean
    public HealthFilter healthFilter(){
        return new HealthFilter();
    }

    /**
     * 开启错误过滤器
     * @return
     */
    @Bean
    public ErrorFilter errorFilter(){
        return new ErrorFilter();
    }
    
//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(corsConfiguration);
//    }

    

}
