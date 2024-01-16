package com.metanet.finalproject.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.member.service.TestService;

import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-43-201-12-132.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class TestController {
	
	@Autowired
	TestService paymentService;
	
	@GetMapping("/memberPhoneCheck")
	public String test() {
		return "test";
	}

	//문자 인증
	@PostMapping("/memberphonecheck")
	public @ResponseBody String memberPhoneCheck(@RequestParam(value="to") String to) throws CoolsmsException {
			
		return paymentService.PhoneNumberCheck(to);
	}
	
}
