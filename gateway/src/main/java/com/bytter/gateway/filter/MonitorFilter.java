package com.bytter.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.concurrent.TimeUnit;

/**
 * 请求统计，按请求的服务分别统计次数。
 */
@Slf4j
public class MonitorFilter extends ZuulFilter {
    private static final Counter COUNTER = Counter.builder("Http请求统计")
            .tag("HttpCount", "HttpCount")
            .description("Http请求统计")
            .register(Metrics.globalRegistry);
//    private static final Timer timer = Timer.builder("QPS")
//            .tag("QPS","QPS")
//            .description("http请求QPS")
//            .register(new SimpleMeterRegistry());
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        COUNTER.increment();
//        timer.record(()->{
////            try {
////                TimeUnit.SECONDS.sleep(2);
////            }catch (InterruptedException e){
////                //ignore
////            }
//
//        });
        log.info(String.valueOf(COUNTER.measure()));
        return null;
    }
}
