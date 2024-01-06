package com.metanet.finalproject.orders.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.service.IFilesService;
import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	IFilesService fileService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	IAddressService addressService;
	
	@Autowired
	ILaundryCategoryService laundryCategoryService;
	
	@Autowired
	ILaundryService laundryService;

	Files files;

	@Operation(summary = "주문")
	@GetMapping("")
	public String getOrder(Model model) {
		List<Orders> orders = ordersService.searchOrder(1);
		System.out.println(orders);
		model.addAttribute("orders", orders);
		return "member/orders_view";
	}

	@Operation(summary = "회원별 주문 조회")
	@GetMapping("/{memberId}")
	@ResponseBody
	public List<Orders> searchOrder(Model model, @PathVariable int memberId) {
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
	public List<Orders> searchOrder(Model model, @PathVariable int memberId, @PathVariable int washId) {
//		System.out.println(memberId + " : m w : " + washId);
		log.info("memberId : {}, washId : {}", memberId, washId);
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder(Model model) {
		// 임시데이터
		Member member = memberService.getMember(1);
		List<Address> addressList = addressService.getAddress(1);
		List<LaundryCategory> laundryCategoryList = laundryCategoryService.getLaundryCategory();
		// System.out.println("laundryCategoryList>>>>"+laundryCategoryList);
		List<Laundry> laundryList = laundryService.getLaundryCategory(1);
		System.out.println("laundryList>>>>>>>>>>>>>>>>" + laundryList);
		model.addAttribute("member", member);
		model.addAttribute("addressList", addressList);
		model.addAttribute("laundryCategoryList", laundryCategoryList);
		model.addAttribute("laundryList", laundryList);
		return "member/orders_insert";
	}

	/*
	 * @Operation(summary = "주문 입력")
	 * 
	 * @PostMapping("/insert") public String insertOrder(Model model, Principal
	 * principal) throws IOException { System.out.println(orders);
	 * 
	 * if (!file.isEmpty()) { orders.setOrdersImageData(file.getBytes());
	 * ordersService.insertOrder(orders); } else { orders.setOrdersImageData(null);
	 * ordersService.insertOrder(orders); }
	 * System.out.println("=============================");
	 * 
	 * return "redirect:/orders/insertok"; }
	 */

	@Operation(summary = "주문 완료 view")
	@GetMapping("/insertok")
	public String insertOkOrder(Model model) {
		System.out.println("======================");
		return "member/orders_insert_ok";
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
