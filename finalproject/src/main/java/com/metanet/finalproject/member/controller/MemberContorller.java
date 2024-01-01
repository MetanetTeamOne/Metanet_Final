package com.metanet.finalproject.member.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.finalproject.member.model.MemberDto;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.temppackage.tempParam;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//@Controller
//@RequestMapping("/")
//public class MemberContorller {
//	@Autowired
//	IMemberService studentService;	
//
//	private static Logger logger = LoggerFactory.getLogger(MemberContorller.class.getName());
//	
//	// 회원 로그인
//	@Operation(summary="비회원 로그인")
//	@GetMapping("/login")
//	public String login() {
//		return "member/login";
//	}
//	
//	@Operation(summary="비회원 로그인")
//	@GetMapping("/login")
//	public String login(@RequestParam tempParam tempParam) {
//		return null;
//	}
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
//
//	
////	   @GetMapping("/test_jwt")
////	   public String testJwt(HttpServletRequest request) {//나는 헤더에서 뽑을 거라서
////	      String token = tokenProvider.resolveToken(request);//요청헤더에 있는 토큰 읽기
////	      log.info("token {}",token); //권장
////	      //log.info("token"+token);//이건 xx
////	      
////	      Authentication auth = tokenProvider.getAuthentication(token);
////	      log.info("principal {}, name {}, authorities{}", auth.getPrincipal(), auth.getName(), auth.getAuthorities());
////	      log.info("isValid {}", tokenProvider.validateToken(token));
////	      return tokenProvider.getUserId(token);
////	   }
//	   
//	// 회원 조회
//	@Operation(summary="일반 회원용 회원 조회")
//	@GetMapping("/")
//	public String getMember() {
//		return null;
//	}
//	
//	// 회원 가입
//	@Operation(summary="일반 회원용 회원 가입	")
//	@PostMapping("/insert")
//	public String insertMember() {
//		return null;
//	}
//	
//	@Operation(summary="일반 회원용 회원 가입	")
//	@GetMapping("/insert")
//	public String insertMember(@RequestBody tempParam tempParam) {
//		return null;
//	}
//	
//	// 회원 수정
//	@Operation(summary="일반 회원용 회원 수정")
//	@GetMapping("/update")
//	public String updateMember() {
//		return null;
//	}
//	
//	@Operation(summary="일반 회원용 회원 수정")
//	@PostMapping("/update")
//	public String updateMember(@RequestBody tempParam tempParam) {
//		return null;
//	}
//	
//	// 회원 삭제
//	@Operation(summary="일반 회원용 회원 삭제")
//	@GetMapping("/delete")
//	public String deleteMember() {
//		return null;
//	}
//	
//	@Operation(summary="일반 회원용 회원 삭제")
//	@PostMapping("/delete")
//	public String deleteMember(@RequestBody tempParam tempParam) {
//		return null;
//	}
//	
//	// 회원 신고
//	@Operation(summary="일반 회원용 회원 신고")
//	@GetMapping("/report")
//	public String reportMember() {
//		return null;
//	}
//	
//	@Operation(summary="일반 회원용 회원 신고")
//	@PostMapping("/report")
//	public String reportMember(@RequestBody tempParam tempParam) {
//		return null;
//	}
	
//}
