package com.self.service.util.common;

import java.io.IOException;

import static com.self.service.util.impl.PropertyFiles.*;

public class CommonProperties {
	
	static CommonPropBean commonProp = new CommonPropBean();
	
	static{
		getAllCommonProperties();
	}
	
	private static void  getAllCommonProperties(){
		PropertyLoaderUtil propUtil = new PropertyLoaderUtil();
		try {
			propUtil.loadProperty(COMMON_PROP, commonProp);
		} catch (ClassNotFoundException | IllegalAccessException | IOException e) {
			//do nothing if property cannot be loaded. Not a big deal.
		}
	}
	
	public static String getDateFormat(){
		return commonProp.getDateFormat();
	}
	
	public static String getWritableDirectory(){
		return commonProp.getWritableDirectory();
	}
}
