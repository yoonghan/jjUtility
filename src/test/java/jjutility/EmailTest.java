package jjutility;

import org.junit.Test;

import com.self.service.util.email.EmailUtil;

public class EmailTest {

	@Test
	public void testEmail(){
		String message = "Hello there";
		try{
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendEmail(message);
		}catch(Exception e){
			System.err.println("Exception");
			e.printStackTrace();
		}
		
	}
}
