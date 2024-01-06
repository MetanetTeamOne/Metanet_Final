package com.metanet.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// 홈
	@GetMapping("/")
	public String home() {
		return "member/home";
	}

	// 서비스 안내
	@GetMapping("/svcinfo")
	public String serviceInformaion() {
		return "member/service_information";
	}
	
	// 이용 방법
	@GetMapping("/howuse")
	public String howUse() {
		return "member/how_use";
	}
	
	/* 관리자 */
	// 관리자 홈
	@GetMapping("/admin")
	public String adminHome() {
		return "admin/adminHome";
	}
	
	// 회원 관리
	@GetMapping("/admin/member")
	public String adminMember() {
		return "admin/adminMember";
	}
	
	// 게시판 관리
	@GetMapping("/admin/board")
	public String adminBoard() {
		return "admin/adminBoard";
	}
	
	// 세탁물 관리
	@GetMapping("/admin/laundry")
	public String adminLaundry() {
		return "admin/adminLaundry";
	}
	
	// 주문 관리
	@GetMapping("/admin/order")
	public String adminOrder() {
		return "admin/adminOrder";
	}
	
	// 결제 관리
	@GetMapping("/admin/pay")
	public String getPay() {
		return "admin/adminPay";
	}
	
	// 매출 관리
	@GetMapping("/admin/chart")
	public String getCharts() {
		return "admin/adminChart";
	}

}
