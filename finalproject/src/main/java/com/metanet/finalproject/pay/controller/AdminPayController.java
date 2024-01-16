package com.metanet.finalproject.pay.controller;

import java.util.List;

import com.metanet.finalproject.paging.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
@RequestMapping("/admin/pay")
@Tag(name = "Pay", description = "결제 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
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
		int payCount = payService.getPayCountByState("1");
		log.info("payCount: {}", payCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(payCount);
		model.addAttribute("pagination", pagination);
//		List<Pay> pays = payService.getPay();
		List<Pay> pays = payService.getPagingPayState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), "1");
		model.addAttribute("pays",pays);
		model.addAttribute("members",members);
		return "admin/adminPay";
	}

	@Operation(summary = "결제 정보 비동기 조회 view")
	@GetMapping("/async")
	public String getpayAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
							  @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
							  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
							  @RequestParam(value = "state", required = false, defaultValue = "1") String state,
							  Model model) {
		log.info("결제 비동기 조회");
		List<Member> members = memberService.getMemberList();
		int payCount = payService.getPayAllCountByState(state);
		log.info("payCount: {}", payCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(payCount);
		model.addAttribute("pagination", pagination);
//		List<Pay> pays = payService.getPay();
		List<Pay> pays = payService.getPagingPayState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), state);
		model.addAttribute("pays",pays);
		model.addAttribute("members",members);
		return "admin/adminPay:: memberTable";
	}
	
	//결제 상태 조회
	@Operation(summary = "결제 상태 정보 조회")
	@GetMapping("/search/{payState}")
	public String getPayState(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
							  @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
							  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, @PathVariable String payState, Model model){
		log.info("결제 상태 정보 조회");
		log.info("payState: {}", payState);
		List<Member> members = memberService.getMemberList();
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		int payCount = payService.getPayAllCountByState(payState);
		log.info("payCount: {}", payCount);
		pagination.setTotalRecordCount(payCount);
		model.addAttribute("pagination", pagination);
//		List<Pay> pays = payService.getPayState(payState);
		List<Pay> pays = payService.getPagingPayState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), payState);
		log.info("pays: {}", pays);
		model.addAttribute("pays", pays);
		model.addAttribute("members",members);
		return "admin/adminPay:: memberTable";
	}

}
