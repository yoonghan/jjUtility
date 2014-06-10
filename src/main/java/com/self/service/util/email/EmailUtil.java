package com.self.service.util.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.self.service.util.impl.PropertyFiles;
import com.self.service.util.log.PropertyLoaderUtil;

public class EmailUtil {
	private final String PROPERTY_FILENAME=PropertyFiles.EMAIL_PROP;
	private final Properties properties  = new Properties();
	private String toUser;
	private String fromUser;
	private String subject;
	private String username;
	private String password;
	
	private final String CLASS_LOCATION="com.self.service.util.email.EmailUtil";
	
	public EmailUtil() throws IOException, ClassNotFoundException, IllegalAccessException{
		initProperties();
	}
	
	private void initProperties() throws IOException, ClassNotFoundException, IllegalAccessException{
		new PropertyLoaderUtil().loadProperty(properties, CLASS_LOCATION, PROPERTY_FILENAME);
		loadValues();
	}
	
	private void loadValues() throws IllegalAccessException{
		
		toUser = properties.getProperty(PropertyFiles.TO_USER);
		if(toUser == null || toUser.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.TO_USER)); 
		}
		fromUser = properties.getProperty(PropertyFiles.FROM_USER);
		if(fromUser == null || fromUser.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.FROM_USER));
		}
		subject = properties.getProperty(PropertyFiles.SUBJECT);
		if(subject == null || subject.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.SUBJECT));
		}
		username = properties.getProperty(PropertyFiles.USER_NAME);
		if(username == null || username.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.USER_NAME));
		}
		password = properties.getProperty(PropertyFiles.PASSWORD);
		if(password == null || password.isEmpty()){
			throw new IllegalAccessException(String.format("Define %s:",PropertyFiles.PASSWORD));
		}
		
	}
	
	/**
	 * Very simple email sending.
	 */
	public void sendEmail(String message){
		sendEmail(subject,message);
	}
		

	/**
	 * Very simple email sending.
	 * @param subject
	 * @param message
	 */
	public void sendEmail(String subject,String message){
		
		if(properties == null){
			System.err.println("Nothing to be loaded");
		}
		
		try{
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			};
			
			Session session = Session.getInstance(properties,auth);
			
	         // Create a default MimeMessage object.
	         MimeMessage mimeMessage = new MimeMessage(session);

	         // Set From: header field of the header.
	         mimeMessage.setFrom(new InternetAddress(fromUser));

	         // Set To: header field of the header.
	         mimeMessage.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(toUser));

	         // Set Subject: header field
	         mimeMessage.setSubject(subject);

	         // Now set the actual message
	         mimeMessage.setText(message);

	         // Send message
	         Transport.send(mimeMessage);
	      }catch (Exception e) {
	         e.printStackTrace();
	         //can't do much just give it as error.
	      }
	}
}
