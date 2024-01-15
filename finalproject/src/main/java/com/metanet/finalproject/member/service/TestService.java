package com.metanet.finalproject.member.service;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class TestService {

	//휴대폰번호 인증문자 보내기
	public String PhoneNumberCheck(String to) throws CoolsmsException{
		String api_key = "NCSHJUW5DCMXSS7Y";
		String api_secret = "UXVPXD24NSMYAELLTR5BW4RLVMSRAVVL";
		Message coolsms = new Message(api_key, api_secret);
			
		
		Random rand = new Random(); 
		String numStr = "";
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10)); 
			numStr += ran;
		}
		  
		HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", to);    
	    params.put("from", "01079196032");    
	    params.put("type", "sms"); 
	    params.put("text", "워시워시 인증번호는 [" + numStr + "] 입니다.");
	 
	    coolsms.send(params); // 메시지 전송
			  
			  
		return numStr;
		 
	}
	
}
