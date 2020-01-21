package com.bytter.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.Enumeration;
@Slf4j
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable ex = ctx.getThrowable();
        try {
            String errorCause="Zuul-Error-Unknown-Cause";
            int responseStatusCode;

            if (ex instanceof ZuulException) {
                String cause = ((ZuulException) ex).errorCause;
                if(cause!=null) errorCause = cause;
                responseStatusCode = ((ZuulException) ex).nStatusCode;

                Enumeration<String> headerIt = ctx.getRequest().getHeaderNames();
                StringBuilder sb = new StringBuilder(ctx.getRequest().getRequestURI()+":"+errorCause);
                while (headerIt.hasMoreElements()) {
                    String name = (String) headerIt.nextElement();
                    String value = ctx.getRequest().getHeader(name);
                    sb.append("REQUEST:: > " + name + ":" + value+"\n");
                }
                log.error(sb.toString());
            }else{
                responseStatusCode = 500;
            }

            ctx.setResponseStatusCode(responseStatusCode);


            ctx.addZuulResponseHeader("Content-Type", "application/json; charset=utf-8");
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("{\"Message\":\""+errorCause+"\",code:500}");
        } finally {
            //ctx.set; //ErrorResponse was handled
            return null;
        }
    }
}
