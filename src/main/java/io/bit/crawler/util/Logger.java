package io.bit.crawler.util;

import java.util.Date;

public class Logger {
	
	private String className;
	
	private Logger(String classname) {
		this.className = classname;
	}
	
	public static final Logger getLogger(Class<?> clazz) {
		return new Logger(clazz.getCanonicalName());
	}
	
	public void info(String msg) {
		log("INFO  " + msg);
	}
	
	public void debug(String msg) {
		log("DEBUG " + msg);
	}
	
	public void error(String msg) {
		log("ERROR " + msg);
	}
	
	public void trace(String msg) {
		log("TRACE " + msg);
	}
	
	public void warn(String msg) {
		log("WARN  " + msg);
	}
	
	public void error(String msg, Throwable t) {
		log("ERROR + " + msg + " " + t.toString());
		t.printStackTrace();
	}
	
	private void log(String msg) {
		System.out.println(new Date(System.currentTimeMillis()) + " [" + className + "] " + msg);
	}

}
