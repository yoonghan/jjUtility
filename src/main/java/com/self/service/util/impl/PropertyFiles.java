package com.self.service.util.impl;

public interface PropertyFiles {
	public final static String EMAIL_PROP="/email.properties";
	public final static String LOG_PROP="/log4j.properties";
	
	//for email properties.
	final String TO_USER="to.user";
	final String FROM_USER="from.user";
	final String SUBJECT="subject";
	final String USER_NAME="mail.user";
	final String PASSWORD="mail.password";
}
