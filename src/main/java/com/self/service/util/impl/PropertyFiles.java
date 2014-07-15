package com.self.service.util.impl;

public interface PropertyFiles {
	public final static String EMAIL_PROP="/email.properties";
	public final static String COMMON_PROP="/common.properties";
	
	//for email properties.
	final String TO_USER="to.user";
	final String FROM_USER="from.user";
	final String SUBJECT="subject";
	final String USER_NAME="mail.user";
	final String PASSWORD="mail.password";
	
	//common file settings.
	final String DATE_FORMAT="date.format";
	final String WRITABLE_DIRECTORY="writable.directory";
}
