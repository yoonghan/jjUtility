package com.self.service.util.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.self.service.util.impl.PropertyMapperImpl;

public class PropertyLoaderUtil{

	final String CLASS_LOCATION = this.getClass().getName();
	
	public PropertyLoaderUtil(){
		
	}
	

	/**
	 * Change configuration using bean.
	 * @param CLASS_LOCATION
	 * @param PROPERTY_FILENAME
	 * @param propertyMapper
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PropertyMapperImpl loadProperty(final String PROPERTY_FILENAME, PropertyMapperImpl propertyMapper)
			throws ClassNotFoundException, IOException, IllegalAccessException{
		
		
		Properties property = new Properties();
		
		InputStream inputStream = loadFile(PROPERTY_FILENAME);
		property.load(inputStream);
		inputStream.close();
		
		propertyMapper.map(property);
		
		property = null;
		
		return propertyMapper;
	}
	
	private InputStream loadFile(final String PROPERTY_FILENAME) throws ClassNotFoundException, FileNotFoundException{
		
		InputStream input = Class.forName(
				CLASS_LOCATION).getClassLoader().getResourceAsStream(
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
