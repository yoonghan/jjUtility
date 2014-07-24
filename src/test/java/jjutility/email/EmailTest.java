package jjutility.email;

import org.junit.Ignore;
import org.junit.Test;

import com.jaring.jom.util.email.EmailUtil;

public class EmailTest {

	@Ignore
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
