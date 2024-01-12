package com.metanet.finalproject.pay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/pay")
@Tag(name = "Pay", description = "결제 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminPayController {

	@Autowired
	IPayService payService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		String token = "";

		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")){
					token = cookie.getValue();
				}
			}
		} catch(Exception e) {
			e.getMessage();
		}

		return jwtTokenProvider.getUserId(token);
	}
	
	@Operation(summary = "결제 정보 조회 view")
	@GetMapping("")
	public String getpay(Model model) {
		List<Member> members = memberService.getMemberList();
		List<Pay> pays = payService.getPay();
		model.addAttribute("pays",pays);
		model.addAttribute("members",members);
		return "admin/adminPay";
	}
	
	//결제 상태 조회
	@Operation(summary = "결제 상태 정보 조회")
	@GetMapping("/search/{payState}")
	public String getPayState(HttpServletRequest request, @PathVariable String payState, Model model){
		List<Member> members = memberService.getMemberList();
		List<Pay> pays = payService.getPayState(payState);
		model.addAttribute("pays", pays);
		model.addAttribute("members",members);
		return "admin/adminPay:: memberTable";
	}

}
