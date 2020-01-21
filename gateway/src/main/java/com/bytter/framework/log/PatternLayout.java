/**
 * 
 */
package com.bytter.framework.log;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author shs
 *
 */
public class PatternLayout {
	private static String SYS_ID = "CMS";
	public void setUpLayout() {
		try {
			ThreadContext.put("ip", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ThreadContext.put("sysid", SYS_ID);
		ThreadContext.put("prj", SYS_ID);
		ThreadContext.put("pid", SYS_ID);
		ThreadContext.put("tid", String.valueOf(Thread.currentThread().getId()));
		
		
	}
	public void clearThreadContext() {
		ThreadContext.clearMap();
	}
	

}
