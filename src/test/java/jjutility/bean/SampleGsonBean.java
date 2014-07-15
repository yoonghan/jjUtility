package jjutility.bean;

import java.util.Calendar;
import java.util.Date;

public class SampleGsonBean{
	
	private Date sampleDate;
	private String sampleString;
	
	public SampleGsonBean(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 12, 11);
		calendar.setTimeInMillis(0);
		sampleDate = calendar.getTime(); //this is not thread safe date retrieval
		sampleString = new String("Test");
	}
	
	public void setSampleDate(Date d){
		sampleDate = d;
	}
	
	public void setSampleString(String str){
		sampleString = str;
	}
		
	public java.util.Date getSampleDate(){
		return sampleDate;
	}
	
	public String getSampleString(){
		return sampleString;
	}
}
