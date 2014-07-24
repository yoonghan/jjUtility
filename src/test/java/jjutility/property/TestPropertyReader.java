package jjutility.property;

import java.io.IOException;

import jjutility.bean.EmailPropertyBean;
import jjutility.bean.SampleGsonBean;
import junit.framework.Assert;

import org.junit.Test;

import com.jaring.jom.util.common.CommonProperties;
import com.jaring.jom.util.common.PropertyLoaderUtil;
import com.jaring.jom.util.gson.CustomGson;
import com.jaring.jom.util.impl.PropertyFiles;

public class TestPropertyReader {

	@Test
	public void testPropertyReader(){
		
		EmailPropertyBean propMapper = new EmailPropertyBean(); 
		
		PropertyLoaderUtil propUtil = new PropertyLoaderUtil();
		try {
			propUtil.loadProperty(PropertyFiles.EMAIL_PROP, propMapper);
		} catch (ClassNotFoundException | IOException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail();
		}
		System.out.println(propMapper.getFromUserValue()+","+propMapper.getToUserValue());
		Assert.assertTrue(propMapper.getFromUserValue().isEmpty() == false);
		Assert.assertTrue(propMapper.getToUserValue().isEmpty() == false);
	}
	
	@Test
	public void testCommonProperties(){
		Assert.assertEquals("dd/MM/yyyy hh:mm:ss", CommonProperties.getDateFormat());
	}
	
	@Test
	public void testGson(){
		SampleGsonBean sampleGson = new SampleGsonBean();
		String jsonValue = CustomGson.toGson(sampleGson);
		
		SampleGsonBean converted = CustomGson.fromGson(jsonValue, SampleGsonBean.class);
		Assert.assertEquals(converted.getSampleDate().getTime(), sampleGson.getSampleDate().getTime());
	}
}
