package com.self.service.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static com.self.service.util.common.CommonProperties.*;

public class CustomGson {
	
	
	final static Gson gson = new GsonBuilder().setDateFormat(getDateFormat()).create();
	
	public CustomGson(){
	}
			
	public static Gson getGson(){
		return gson;
	}
	
	public static <T> String toGson(T object){
		return gson.toJson(object);
	}
	
	public static <T> T fromGson(String jsonValue, Class<T> objectType){
		return gson.fromJson(jsonValue, objectType);
	}
}
