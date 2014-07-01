package jjutility.property;

import java.io.IOException;

import jjutility.bean.EmailPropertyBean;
import junit.framework.Assert;

import org.junit.Test;

import com.self.service.util.common.PropertyLoaderUtil;
import com.self.service.util.impl.PropertyFiles;

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
}
