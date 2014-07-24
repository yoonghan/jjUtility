package jjutility.bean;

import java.util.Properties;

import com.jaring.jom.util.impl.PropertyMapperImpl;

public class EmailPropertyBean implements PropertyMapperImpl{

	private String toUser="to.user";
	private String fromUser="from.user";
	
	private String toUserValue = "";
	private String fromUserValue = "";
	
	@Override
	public void map(Properties property) throws IllegalArgumentException {
		setToUserValue(property.getProperty(toUser));
		setFromUserValue(property.getProperty(fromUser));
	}

	public String getToUserValue() {
		return toUserValue;
	}

	public void setToUserValue(String toUserValue) {
		this.toUserValue = toUserValue;
	}

	public String getFromUserValue() {
		return fromUserValue;
	}

	public void setFromUserValue(String fromUserValue) {
		this.fromUserValue = fromUserValue;
	}
	
	

}
