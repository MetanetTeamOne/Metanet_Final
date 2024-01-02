package com.metanet.finalproject.orders.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.service.FilesService;
import com.metanet.finalproject.files.service.IFilesService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrdersController {
	
//	@Value("${file.upload.directory}")
//	private String uploadDirectory;
	
	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IFilesService fileService;
	
	Files files;
	
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
		
		
		System.out.println(order.getWashId());
		
		System.out.println("111");
		
		return ordersService.searchOrder(memberId, -1);
	}
	
	// 지금 안되고 있는데 해결 해보겠음
	
	@Operation(summary = "회원 회차별 주문 조회")
	@GetMapping("/{memberId}/{washId}")
	@ResponseBody
	public List<Orders> searchOrder(Model model, @PathVariable int memberId, @PathVariable int washId){
//		System.out.println(memberId + " : m w : " + washId);
		log.info("memberId : {}, washId : {}", memberId, washId);
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder(Model model) {
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Model model, Orders orders, @RequestParam MultipartFile file) throws IOException {
		System.out.println(orders);
		
		if (!file.isEmpty()) {
			orders.setOrdersImageData(file.getBytes());
			ordersService.insertOrder(orders);
		}
		else {
			orders.setOrdersImageData(null);
			ordersService.insertOrder(orders);
		}
		
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 수정 view")
	@GetMapping("/update/{ordersId}")
	public String updateOrder(Model model) {
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 수정")
	@PostMapping("/update")
	public String updateOrder(Model model, Orders orders) {
		System.out.println(orders);
		ordersService.updateOrder(orders);
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 삭제")
	@PostMapping("/delete")
	public String deleteOrder(Model model, Orders orders) {
		ordersService.deleteOrder(orders);
		return "member/mypage_order";
	}
}
