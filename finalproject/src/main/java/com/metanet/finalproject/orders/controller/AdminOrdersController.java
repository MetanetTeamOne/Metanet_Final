package com.metanet.finalproject.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class AdminOrdersController {

	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	ILaundryService laundryService;
	
	@GetMapping("/admin/order")
	public String searchOrdersList(Model model) {
		List<Orders> ordersAllList = ordersService.searchOrdersList();
		model.addAttribute("ordersAllList", ordersAllList);
		return "admin/adminOrder";
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
		model.addAttribute("memberEmail", memberEmail);
		model.addAttribute("orderList", orderList);
		model.addAttribute("laundryList", laundryList);
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
