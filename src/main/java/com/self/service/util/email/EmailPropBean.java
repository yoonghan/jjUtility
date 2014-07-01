package com.self.service.util.email;

import java.text.SimpleDateFormat;
import java.util.Properties;

import com.self.service.util.impl.PropertyFiles;
import com.self.service.util.impl.PropertyMapperImpl;

class EmailPropBean implements PropertyMapperImpl {

	private Properties property  = new Properties();
	private String toUser;
	private String fromUser;
	private String subject;
	private String userName;
	private String password;
	private SimpleDateFormat dateFormat;
	private final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
	
	@Override
	public void map(Properties property) throws IllegalAccessException {

		setToUser(property.getProperty(PropertyFiles.TO_USER));
		if(toUser == null || toUser.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.TO_USER)); 
		}
		setFromUser(property.getProperty(PropertyFiles.FROM_USER));
		if(fromUser == null || fromUser.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.FROM_USER));
		}
		setSubject(property.getProperty(PropertyFiles.SUBJECT));
		if(subject == null || subject.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.SUBJECT));
		}
		setUserName(property.getProperty(PropertyFiles.USER_NAME));
		if(userName == null || userName.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.USER_NAME));
		}
		setPassword(property.getProperty(PropertyFiles.PASSWORD));
		if(password == null || password.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.PASSWORD));
		}
		setDateFormat(new SimpleDateFormat(property.getProperty(PropertyFiles.DATE_FORMAT, DEFAULT_DATE_FORMAT)));
		
		setProperty(property);
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Properties getProperty() {
		return property;
	}
	
	public void setProperty(Properties property) {
		this.property = property;
	}

}
