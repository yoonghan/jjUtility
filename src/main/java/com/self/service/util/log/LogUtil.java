package com.self.service.util.log;

import java.util.HashMap;

public class LogUtil {

	private final static int LOG_SIZE = 200;
	
	private final static HashMap<String, LogUtil> setOfLogs = new HashMap<String,LogUtil>(LOG_SIZE); 
	
	private final String CLASS_NAME_LOG;
	
	private LogUtil(String CLASS_NAME_LOG){
		this.CLASS_NAME_LOG = CLASS_NAME_LOG;
	}
	
	private synchronized static LogUtil createLog(String className){
		LogUtil logUtil = new LogUtil(className);
		setOfLogs.put(className, logUtil);
		return logUtil;
	}
	
	public static LogUtil getInstance(String className){
		if(setOfLogs.containsKey(className)){
			return setOfLogs.get(className);
		}else{
			return createLog(className);
		}
	}
	
	public void error(String error){
		System.err.println(String.format("%s : %s",CLASS_NAME_LOG,error));
	}
	
	public void error(String error, Exception e){
		System.err.println(String.format("%s : %s",CLASS_NAME_LOG,error));
		System.err.println(e);
	}
	
	public void info(String info){
		System.out.println(String.format("%s : %s",CLASS_NAME_LOG,info));
	}
	
	public void warn(String warn){
		System.out.println(String.format("WARNING: %s : %s",CLASS_NAME_LOG,warn));
	}
}
