/**
 * 业务功能日志，封装日志组件，方便系统在有需要时更换日志组件，
 * 而不影响到业务功能使用。
 */
package com.bytter.framework.log;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author shs
 *
 */
public class FeaturesLog {
	protected Logger log;
	public FeaturesLog(String className) {
		log =  LoggerFactory.getLogger(className);
				
	}
	public Logger getLog(String className) {
		log = LoggerFactory.getLogger(className);
		return log;
	}
	
	public void warn(String msg) {
		log.warn(msg);
	}
	
	public void error(String msg) {
		log.error(msg);
	}
	
	public void error(Throwable e) {
		log.error("Exception:",e);
	}
	
	public void info(Map<String, String> map,String msg) {
		PatternLayout pl = new PatternLayout();
		pl.setUpLayout();
		log.info(msg);
		pl.clearThreadContext();
	}
	
	public void debug(String msg) {
		log.debug(msg);
	}
}
