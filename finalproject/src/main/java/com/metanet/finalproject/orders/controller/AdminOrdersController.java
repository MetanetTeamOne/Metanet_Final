package com.metanet.finalproject.orders.controller;

import java.util.List;

import com.metanet.finalproject.paging.Pagination;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.member.service.MemberService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-43-201-12-132.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminOrdersController {

	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	ILaundryService laundryService;
	
	@Autowired
	IAddressService addressService;
	
	@Autowired
	IPayService payService;
	
	@GetMapping("/admin/order")
	public String searchOrdersList(Model model) {
//		List<Orders> ordersAllList = ordersService.searchOrdersList();

		int orderCount = ordersService.getOrderCount();
		log.info("orderCount: {}", orderCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(orderCount);
		model.addAttribute("pagination", pagination);
		List<Orders> ordersAllList = ordersService.searchPagingOrdersList(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex());
		model.addAttribute("ordersAllList", ordersAllList);
		return "admin/adminOrder";
	}

	@GetMapping("/admin/order/async")
	public String searchOrdersListAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
										@RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
										@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {
//		List<Orders> ordersAllList = ordersService.searchOrdersList();

		int orderCount = ordersService.getOrderCount();
		log.info("orderCount: {}", orderCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(orderCount);
		model.addAttribute("pagination", pagination);
		List<Orders> ordersAllList = ordersService.searchPagingOrdersList(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex());
		model.addAttribute("ordersAllList", ordersAllList);
		return "admin/adminOrder:: memberTable";
	}
	
//	@GetMapping("/all")
//	@ResponseBody
//	public List<Orders> searchOrdersList() {
//		List<Orders> ordersAllList = ordersService.searchOrdersList();
//		return ordersAllList;
//	}
	
	@GetMapping("/admin/order/view/{memberId}/{washId}")
	public String searchOrdersView(@PathVariable int memberId, @PathVariable int washId, Model model) {
		//List<Orders> orderList = ordersService.searchOrder(memberId);
		List<Orders> orderList = ordersService.searchOrder(memberId, washId);
		List<Laundry> laundryList = laundryService.getLaundry();
		String memberEmail = memberService.getMember(memberId).getMemberEmail();
		Address address = addressService.getAddressByMemberId(memberId);
		List<Pay> payList = payService.getMemberPay(memberId);
		System.out.println("laundryList======"+laundryList);
		System.out.println("address============="+address);
		Pay pay = payService.getWashIdPay(washId);
		
		model.addAttribute("memberEmail", memberEmail);
		model.addAttribute("orderList", orderList);
		model.addAttribute("laundryList", laundryList);
		model.addAttribute("address", address);
		model.addAttribute("payList", payList);
		model.addAttribute("pay", pay);
		return "admin/adminOrderView";
	}
	
//	@GetMapping("/view/{memberId}/{washId}")
//	@ResponseBody
//	public List<Orders> searchOrdersView(@PathVariable int memberId, @PathVariable int washId) {
//		//List<Orders> orderList2 = ordersService.searchOrder(memberId);
//		List<Orders> orderList =ordersService.searchOrder(memberId, washId);
//		return orderList;
//	}

	
	@PostMapping("/admin/order/update")
	public String updateStatus(@RequestParam("washId") int washId, @RequestParam("ordersStatus") String ordersStatus) {
		System.out.println("washId"+washId);
		System.out.println("ordersStatus"+ordersStatus);
		ordersService.updateStatus(washId, ordersStatus);
		return "redirect:/admin/order";
	}
	
}
