package com.metanet.finalproject.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.member.service.TestService;

import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
public class TestController {
	
	@Autowired
	TestService paymentService;
	
	@Autowired
	IMemberService memberService;
	
	@GetMapping("/memberPhoneCheck")
	public String test() {
		return "test";
	}

	//문자 인증
	@PostMapping("/memberphonecheck")
	public @ResponseBody String memberPhoneCheck(@RequestParam(value="to") String to) throws CoolsmsException {
		return paymentService.PhoneNumberCheck(to);
	}
	
	@GetMapping("/phone/check")
    @ResponseBody
    public int getPhoneCount(String memberPhoneNumber){
    	return memberService.getPhoneCount(memberPhoneNumber);
    }
}
