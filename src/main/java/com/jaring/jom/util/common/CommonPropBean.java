package com.jaring.jom.util.common;

import com.jaring.jom.util.impl.PropertyMapperImpl;

import static com.jaring.jom.util.impl.PropertyFiles.*;

public class CommonPropBean implements PropertyMapperImpl{

	private final static String DEFAULT_DATE_FORMAT="dd/MM/yyyy hh:mm:ss";
	
	//Make this the default directory, as this is openshift's writable folder.
	private final static String DEFAULT_WRITABLE_DIRECTORY=System.getProperty("user.home")+"/misc";
	
	private String dateFormat = DEFAULT_DATE_FORMAT;
	private String writableDirectory = DEFAULT_WRITABLE_DIRECTORY;
	
	//package availability only.
	CommonPropBean(){
		
	}
	
	@Override
	public void map(java.util.Properties property) throws IllegalAccessException {
		dateFormat = property.getProperty(DATE_FORMAT);
		if(dateFormat == null || dateFormat.isEmpty()){
			dateFormat = DEFAULT_DATE_FORMAT;
		}
		writableDirectory = property.getProperty(WRITABLE_DIRECTORY);
		if(writableDirectory == null || writableDirectory.isEmpty()){
			writableDirectory = DEFAULT_WRITABLE_DIRECTORY;
		}
	};
	
	public String getDateFormat(){
		return dateFormat;
	}
	
	public String getWritableDirectory(){
		return writableDirectory;
	}
}
