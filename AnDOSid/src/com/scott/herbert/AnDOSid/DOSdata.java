package com.scott.herbert.AnDOSid;

import java.util.Random;

public class DOSdata {
	private String Target;
	private String Payload;
	private String IMEI;
	private String androidId;
	private int Milliseconds;

	private String httpUserAgent = "AnDOSid - Android based Http post flooder, for help see http://scott-herbert.com/blog/andosid";
	private String [] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
	"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
	"1","2","3","4","5","6","7","8","9","0"};
	
	public void setTarget(String inTarget){
		Target = inTarget;
	}
	public void setIMEI(String inIMEI){
		IMEI = inIMEI;
	}
	public void setAndroidId(String inandroidId){
		androidId = inandroidId;
	}
	
	public void setPayload(int size){
	
		Random rnd = new Random();
		String TempStr;
		TempStr = "";
		for(;size > 0;){
			TempStr = TempStr.concat(letters[rnd.nextInt(letters.length)]);
			size--;
		}
		Payload = TempStr;
		
	}
	public void setMilliseconds(int i){
		Milliseconds = i;
	}
	
	public int getMilliseconds(){
		return Milliseconds;
	}
	
	public String getTarget(){
		return Target;
	}
	public String getIMEI(){
		return IMEI;
	}
	public String getAndroidId(){
		return androidId;
	}
	public String getPayload(){
		return Payload;
	}
	public String getHttpUserAgent(){
		return httpUserAgent;
	}
	
	DOSdata() {
		
	}
	
}
