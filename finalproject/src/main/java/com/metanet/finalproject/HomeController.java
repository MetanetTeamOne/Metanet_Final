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
	
	// 회원가입
	@GetMapping("/signup")
	public String signup() {
		System.out.println("signup");
		return "member/signup";
	}
	
	// 회원가입 완료
	@GetMapping("/signupok")
	public String signupOk() {
		System.out.println("signupok");
		return "member/signup_ok";
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

	// 세탁 신청
	@GetMapping("/register")
	public String register() {
		System.out.println("register");
		return "member/laundry_register";
	}
	
	// 마이페이지
	@GetMapping("/mypage")
	public String mypage() {
		System.out.println("mypage");
		return "member/mypage_account";
	}
	
	// 마이페이지_계정관리
		@GetMapping("/mypageaccount")
		public String mypageAccount() {
			System.out.println("mypageAccount");
			return "member/mypage_account";
		}
	
	// 마이페이지_비밀번호 관리
	@GetMapping("/mypagepw")
	public String mypagePassword() {
		System.out.println("mypagePassword");
		return "member/mypage_password";
	}
	
	// 마이페이지_구독관리
	@GetMapping("/mypagesub")
	public String mypageSubscribe() {
		System.out.println("mypageSubscribe");
		return "member/mypage_subscribe";
	}
	
	// 마이페이지_카드관리
	@GetMapping("/mypagecard")
	public String mypageCard() {
		System.out.println("mypageCard");
		return "member/mypage_card";
	}
	
	// 마이페이지_주소관리
	@GetMapping("/mypageaddr")
	public String mypageAddress() {
		System.out.println("mypageAddress");
		return "member/mypage_address";
	}
	
	// 마이페이지_주문내역
	@GetMapping("/mypageorder")
	public String mypageOrder() {
		System.out.println("mypageOrder");
		return "member/mypage_order";
	}
	
	// 마이페이지_결제내역
	@GetMapping("/mypagepay")
	public String mypagePay() {
		System.out.println("mypagePay");
		return "member/mypage_pay";
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
