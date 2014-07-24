package com.jaring.jom.util.email;

import java.io.IOException;
import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jaring.jom.util.common.PropertyLoaderUtil;
import com.jaring.jom.util.impl.PropertyFiles;

public class EmailUtil {
	private final String PROPERTY_FILENAME=PropertyFiles.EMAIL_PROP;
	final EmailPropBean EMAILBEAN = new EmailPropBean();
	
	public EmailUtil() throws IOException, ClassNotFoundException, IllegalAccessException{
		initProperties();
	}
	
	private void initProperties() throws IOException, ClassNotFoundException, IllegalAccessException{
		new PropertyLoaderUtil().loadProperty(PROPERTY_FILENAME, EMAILBEAN);
	}
	
	/**
	 * Very simple email sending.
	 */
	public void sendEmail(String message){
		String subjectWithDate = String.format("%s [%s]", EMAILBEAN.getSubject(), EMAILBEAN.getDateFormat().format(new Date()));
		sendEmail(subjectWithDate,message);
	}
		

	/**
	 * Very simple email sending.
	 * @param subject
	 * @param message
	 */
	public void sendEmail(String subject,String message){
		
		if(EMAILBEAN.getProperty() == null){
			System.err.println("Nothing to be loaded");
		}
		
		try{
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(EMAILBEAN.getUserName(), EMAILBEAN.getPassword());
				}
			};
			
			Session session = Session.getInstance(EMAILBEAN.getProperty(),auth);
			
	         // Create a default MimeMessage object.
	         MimeMessage mimeMessage = new MimeMessage(session);

	         // Set From: header field of the header.
	         mimeMessage.setFrom(new InternetAddress(EMAILBEAN.getFromUser()));

	         // Set To: header field of the header.
	         mimeMessage.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(EMAILBEAN.getToUser()));

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
