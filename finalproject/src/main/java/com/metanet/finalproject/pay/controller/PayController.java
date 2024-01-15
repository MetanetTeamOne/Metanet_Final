package com.metanet.finalproject.pay.controller;

import java.util.List;

import com.metanet.finalproject.paging.Pagination;
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
@RequestMapping("/pay")
@Tag(name = "Pay", description = "결제 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class PayController {

	@Autowired
	IPayService payService;
	
	@Autowired
	IMemberService memberService2;
	
	@Autowired
	IMemberService memberService;
	
//	@Autowired
//	IOrderService ordersService;
	
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
	public String getpay() {
		return "member/pay_view";
	}
	
	//pay_ok 폼
	@Operation(summary = "결제 완료 view")
	@GetMapping("/payok")
	public String payOk() {
		return "member/pay_ok";
	}
	
	//결제 상세 조회
//	@Operation(summary = "결제 상세 정보 조회")
//	@GetMapping("/{payId}")
//	public String getPay(@PathVariable int payId, Model model) {
//		Pay pay = payService.getPay(payId);
//		model.addAttribute("pay", pay);
//		return "member/pay_view";
//	}
	
	//결제 상태 조회
	@Operation(summary = "결제 상태 정보 조회")
	@GetMapping("/search/{payState}")
	public String getPayState(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
							  @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
							  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest request, @PathVariable String payState, Model model){
		Member member = memberService.selectMember(getTokenUserEmail(request));
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		int payCount = payService.getPayCountByState(member.getMemberId(), payState);
		pagination.setTotalRecordCount(payCount);
		model.addAttribute("pagination", pagination);

		List<Pay> pays = payService.getPagingMemberPayByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId(), payState);
		model.addAttribute("pays", pays);
		model.addAttribute("member", member);
		return "member/card_view:: memberTable";
	}
	
	//결제 진행 폼
//	@GetMapping("/pay/insert/{payId}")
//	public String insertPay(@PathVariable int payId, Model model) {
//		
//		Pay pay = payService.getPay(payId);
//		//Pay pay2 = payService.getPay(1);
//		model.addAttribute("pay", pay);
//		return "pay/pay_input";
//	}
	
	//결제 진행 처리
//	@Operation(summary = "결제 정보 진행 처리")
//	@PostMapping("/pay/do/{ordersId}")
//	public String insertPay(@PathVariable int ordersId, Pay pay, Model model) {
//		//int ordersId = pay.getOrdersId();
//		try {
//	        String memberSubscribe = memberService2.selectSubscribe("chlrkdls1269@gmail.com");
//	        
//	        // 배송비 : 구독자 0원, 비구독자 2500원
//	        if (memberSubscribe.equals("0")) {
//	            pay.setPayDelivery(2500);
//	        } else if (memberSubscribe.equals("1")) {
//	            pay.setPayDelivery(0);
//	        }
//	        
////	        System.out.println("ordersId 1111: " + ordersId);
//
//	        // pay 객체에 정수 값으로 설정
//	        pay.setOrdersId(ordersId);
//
////	        System.out.println("ordersId 2222: " + pay.getOrdersId());
////	        System.out.println("pay>>>"+pay.getOrdersId());
//	        payService.insertPay(pay);
//	        model.addAttribute("pay", pay);
////	        System.out.println("==결제완료==");
//	        return "redirect:/pay/payok";
//	    } catch (NumberFormatException e) {
//	        // ordersId가 정수로 변환할 수 없는 경우의 예외 처리
//	        e.printStackTrace(); // 또는 로깅
//	        return "error!!!!!!!!!!!!!!!!!!!";
//	    }
//	}
	
}
