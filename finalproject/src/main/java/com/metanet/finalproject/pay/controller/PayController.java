package com.metanet.finalproject.pay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	IPayService payService;
	
	@Autowired
	IMemberService memberService2;
	
//	@Autowired
//	IOrderService ordersService;
	
	@GetMapping("")
	public String getpay() {
		return "member/pay_view";
	}
	
	//pay_ok 폼
	@GetMapping("/pay_ok")
	public String payOk() {
		return "member/pay_ok";
	}
	
	//결제 상세 조회
	@GetMapping("/{payId}")
	public String getPay(@PathVariable int payId, Model model) {
		Pay pay = payService.getPay(payId);
		model.addAttribute("pay", pay);
		return "member/pay_view";
	}
	
	//결제 상태 조회
	@GetMapping("/pay/search/{payState}")
	@ResponseBody
	public List<Pay> getPayState(@PathVariable String payState, Model model){
		List<Pay> pay = payService.getPayState(payState);
		model.addAttribute("pay", pay);
		return pay;
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
	@PostMapping("/pay/do/{ordersId}")
	public String insertPay(@PathVariable int ordersId, Pay pay, Model model) {
		//int ordersId = pay.getOrdersId();
		try {
	        String memberSubscribe = memberService2.selectSubscribe("chlrkdls1269@gmail.com");
	        
	        // 배송비 : 구독자 0원, 비구독자 2500원
	        if (memberSubscribe.equals("0")) {
	            pay.setPayDelivery(2500);
	        } else if (memberSubscribe.equals("1")) {
	            pay.setPayDelivery(0);
	        }
	        
	        System.out.println("ordersId 1111: " + ordersId);

	        // pay 객체에 정수 값으로 설정
	        pay.setOrdersId(ordersId);

	        System.out.println("ordersId 2222: " + pay.getOrdersId());
	        System.out.println("pay>>>"+pay.getOrdersId());
	        payService.insertPay(pay);
	        model.addAttribute("pay", pay);
	        System.out.println("==결제완료==");
	        return "redirect:/pay/pay_ok";
	    } catch (NumberFormatException e) {
	        // ordersId가 정수로 변환할 수 없는 경우의 예외 처리
	        e.printStackTrace(); // 또는 로깅
	        return "error!!!!!!!!!!!!!!!!!!!";
	    }
	}
	
}
