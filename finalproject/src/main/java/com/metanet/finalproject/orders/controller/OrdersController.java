package com.metanet.finalproject.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.service.IFilesService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.member.service.MemberService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@Tag(name = "Orders", description = "주문 관리 API")
public class OrdersController {
	
//	@Value("${file.upload.directory}")
//	private String uploadDirectory;
	
	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemberService memberService;

	@Autowired
	IFilesService fileService;
	
	Files files;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		log.info("이메일로 토큰 받는중...");
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
	
	@Operation(summary = "주문")
	@GetMapping("")
	public String getOrder(HttpServletRequest request, Model model) {
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		List<Orders> orders = ordersService.searchOrder(memberId);
//		System.out.println(orders);
		model.addAttribute("orders", orders);
		return "member/orders_view";
	}
	
	@Operation(summary = "회원별 주문 조회")
	@GetMapping("/{memberId}")
	@ResponseBody
	public List<Orders> searchOrder(Model model, @PathVariable int memberId){
//	List<Orders> searchOrder(HttpServletRequest request){
//	String token = tokenProvider.resolveToken(request);
//  log.info("token {}",token); //권장
//    
//  Authentication auth = tokenProvider.getAuthentication(token);
//  log.info("principal {}, name {}, authorities{}", auth.getPrincipal(), auth.getName(), auth.getAuthorities());
		Orders order = new Orders();
		
		
//		System.out.println(order.getWashId());
//		
//		System.out.println("111");
		
		return ordersService.searchOrder(memberId, -1);
	}
	
	// 지금 안되고 있는데 해결 해보겠음
	
	@Operation(summary = "회원 회차별 주문 조회")
	@GetMapping("/{memberId}/{washId}")
	@ResponseBody
	public List<Orders> searchOrder(Model model, @PathVariable int memberId, @PathVariable int washId){
//		System.out.println(memberId + " : m w : " + washId);
//		log.info("memberId : {}, washId : {}", memberId, washId);
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder(Model model) {
		return "member/orders_insert";
	}
	
	/*@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Model model, Principal principal) throws IOException {
		System.out.println(orders);
		
		if (!file.isEmpty()) {
			orders.setOrdersImageData(file.getBytes());
			ordersService.insertOrder(orders);
		}
		else {
			orders.setOrdersImageData(null);
			ordersService.insertOrder(orders);
		}
		System.out.println("=============================");
		
		return "redirect:/orders/insertok";
	}*/
	
	@Operation(summary = "주문 완료 view")
	@GetMapping("/insertok")
	public String insertOkOrder(Model model) {
//		System.out.println("======================");
		return "member/orders_insert_ok";
	}
	
	@Operation(summary = "주문 수정 view")
	@GetMapping("/update/{ordersId}")
	public String updateOrder(Model model, @PathVariable int ordersId) {
		List<Orders> orders = ordersService.searchOrderId(ordersId);
		model.addAttribute("orders", orders);
		return "member/orders_update";
	}
	
	@Operation(summary = "주문 수정")
	@PostMapping("/update")
	public String updateOrder(Model model, Orders orders) {
//		System.out.println(orders);
		ordersService.updateOrder(orders);
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 삭제")
	@PostMapping("/delete/{ordersId}/{washId}")
	public String deleteOrder(Model model, @PathVariable int ordersId, @PathVariable int washId) {
		ordersService.deleteOrder(ordersId, washId);
		return "member/mypage_order";
	}
}
