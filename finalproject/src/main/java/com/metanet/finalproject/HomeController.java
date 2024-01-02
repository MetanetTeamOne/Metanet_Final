package com.metanet.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// 홈
	@GetMapping("/")
	public String home() {
		System.out.println("home");
		return "member/home";
	}
	
	// 로그인
	@GetMapping("/login")
	public String login() {
		System.out.println("login");
		return "member/login";
	}
	
	// 서비스 안내
	@GetMapping("/svcinfo")
	public String serviceInformaion() {
		System.out.println("svcinfo");
		return "member/service_information";
	}
	
	// 이용 방법
	@GetMapping("/howuse")
	public String howUse() {
		System.out.println("howuse");
		return "member/how_use";
	}

	
	/* 관리자 */
	// 관리자 홈
	@GetMapping("/admin")
	public String adminHome() {
		System.out.println("admin");
		return "admin/adminHome";
	}
	
	// 회원 관리
	@GetMapping("/admin/member")
	public String adminMember() {
		System.out.println("adminMember");
		return "admin/adminMember";
	}
	
	// 게시판 관리
	@GetMapping("/admin/board")
	public String adminBoard() {
		System.out.println("adminBoard");
		return "admin/adminBoard";
	}
	
	// 세탁물 관리
	@GetMapping("/admin/laundry")
	public String adminLaundry() {
		System.out.println("adminLaundry");
		return "admin/adminLaundry";
	}
	
	// 주문 관리
	@GetMapping("/admin/order")
	public String adminOrder() {
		System.out.println("adminOrder");
		return "admin/adminOrder";
	}
	
	// 결제 관리
	@GetMapping("/admin/pay")
	public String getPay() {
		System.out.println("getPay");
		return "admin/adminPay";
	}
	
	// 매출 관리
	@GetMapping("/admin/chart")
	public String getCharts() {
		System.out.println("getCharts");
		return "admin/adminChart";
	}

}
