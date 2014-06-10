package com.self.service.util.log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoaderUtil{

	public PropertyLoaderUtil(){
		
	}
	
	public Properties loadProperty(Properties property, final String CLASS_LOCATION, final String PROPERTY_FILENAME)
	 throws ClassNotFoundException, IOException{
		
		if(property == null){
			property = new Properties();
		}
		
		InputStream inputStream = loadFile(CLASS_LOCATION, PROPERTY_FILENAME);
		property.load(inputStream);
		inputStream.close();
		
		return property;
	}
	
	private InputStream loadFile(final String CLASS_LOCATION, final String PROPERTY_FILENAME) throws ClassNotFoundException, FileNotFoundException{
		InputStream input = Class.forName(
				CLASS_LOCATION)
				.getClassLoader().getResourceAsStream(
						PROPERTY_FILENAME);
		if (input == null)
			input = Class.forName(
					CLASS_LOCATION).getClass()
					.getResourceAsStream(PROPERTY_FILENAME);
		if (input == null)
			input = new java.io.FileInputStream("."+PROPERTY_FILENAME);
		
		return input;
	}
	
}
