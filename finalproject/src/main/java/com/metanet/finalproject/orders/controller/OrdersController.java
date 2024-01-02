package com.metanet.finalproject.orders.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	IOrdersService ordersService;
	
	@Operation(summary = "회원별 주문 조회")
	@GetMapping("")
	public String getOrder(Model model, Principal principal){
//		List<Orders> orderList = ordersService.searchOrder(1);
//		model.addAttribute("orderList", orderList);
//		System.out.println(orderList);
//	List<Orders> searchOrder(HttpServletRequest request){
//	String token = tokenProvider.resolveToken(request);
//  log.info("token {}",token); //권장
//  
//  Authentication auth = tokenProvider.getAuthentication(token);
//  log.info("principal {}, name {}, authorities{}", auth.getPrincipal(), auth.getName(), auth.getAuthorities());
//		ordersService.searchOrder(1);
		return "member/orders_view";
	}
	
	@Operation(summary = "회원 회차별 주문 조회")
	@GetMapping("/{memberId}/{washId}")
	public List<Orders> searchOrder(@PathVariable int memberId, @PathVariable int washId){
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder() {
		return "member/orders_insert";
	}
	
	@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Principal principal, Orders orders) {
		System.out.println(orders);
//		ordersService.insertOrder(orders);
		return "redirect:/orders/insertok";
	}
	
	@Operation(summary = "주문 입력 view")
	@GetMapping("/insertok")
	public String insertOkOrder() {
		return "member/orders_insert_ok";
	}
	
	@Operation(summary = "주문 수정 view")
	@GetMapping("/update/{ordersId}")
	public String updateOrder(Model model) {
		return "member/orders_view";
	}
	
	@Operation(summary = "주문 수정")
	@PostMapping("/update")
	public String updateOrder(Principal principal, Orders orders) {
		ordersService.updateOrder(orders);
		return "redirect:/orders";
	}
	
	@Operation(summary = "주문 삭제")
	@PostMapping("/delete")
	public String deleteOrder() {
		return "redirect:/orders";
	}
}
