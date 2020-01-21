/**
 * log获取主机ip
 */
package com.bytter.framework.log;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author shs
 *
 */
public class MyLogConfig extends ClassicConverter{

	@Override
	public String convert(ILoggingEvent event) {
		try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
		return "noip";
	}

}
