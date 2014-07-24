package com.jaring.jom.util.authentication.facebook;

import java.util.Properties;

import com.jaring.jom.util.impl.PropertyMapperImpl;

public class FacebookBean implements PropertyMapperImpl{

	private String clientId;// = "628261903884404";
	private String clientSecret;// = "d19f8a84c4a0e2b31630c8c9c1701220";
	
	@Override
	public void map(Properties property) throws IllegalAccessException {
		setClientId(property.getProperty("client.id"));
		setClientSecret(property.getProperty("client.secret"));
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
