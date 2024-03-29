package com.metanet.finalproject.orders.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;
import com.metanet.finalproject.paging.Pagination;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-43-201-12-132.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminHomeController {

	@Autowired
	IMemberService memberService;

	@Autowired
	IPayService payService;

	@Autowired
	IMemhelpService memHelpService;

	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemhelpService memhelpService;

	@GetMapping("/admin")
	public String adminHome(Model model, @ModelAttribute("member") Member member, @ModelAttribute("pay") Pay pay,
			@ModelAttribute("memhelp") Memhelp memhelp, @ModelAttribute("orders") Orders orders) {
		String memberJoinState = member.getMemberJoinState();
		int memberCount = memberService.getMemberCount(memberJoinState);
		// System.out.println("memberCount >>>> "+memberCount);
		//int payMoney = pay.getPayMoney();
		int totalPay = payService.sumPay();
		//System.out.println("totalPay>>>>>"+totalPay);
		
		//System.out.println("pay.getPayId()>>"+pay.getPayId());
		//System.out.println("list>>>>>>>>>>>>>>"+payService.getPay());
		List<Pay> payList = payService.getPay();
		List<Date> payDateList = new ArrayList<>();
		List<Integer> payMoneyList = new ArrayList<>();
		
		for (Pay pay2 : payList) {
		    Date payDate = pay2.getPayDate();
		    payDateList.add(payDate);
		}
		
		for (Pay pay2 : payList) {
		    int payMoney = pay2.getPayMoney();
		    payMoneyList.add(payMoney);
		}
		
		//System.out.println("payDateList==========="+payDateList);
		//System.out.println("payMoneyList====="+payMoneyList);
		
		String memHelpState = memhelp.getMemHelpState();
		int totalHelp = memHelpService.countMemHelp(memHelpState);
		
		int totalCount = ordersService.countOrder();
		//System.out.println("totalCount============="+totalCount);
		
		//List<Orders> ordersAllList = ordersService.searchOrdersList();
		
		int orderCount = ordersService.getOrderCount();
		log.info("orderCount: {}", orderCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(orderCount);
		model.addAttribute("pagination", pagination);
		List<Orders> ordersAllList = ordersService.searchPagingOrdersList(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex());
		//model.addAttribute("ordersAllList", ordersAllList);
		
		int memHelpCount = memhelpService.getAdminMemHelpCount();
		log.info("memHelpCount: {}", memHelpCount);
	//	Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(memHelpCount);
		model.addAttribute("pagination", pagination);
//		model.addAttribute("memhelpList", memhelpService.searchAllMemhelp());
		model.addAttribute("memhelpList", memhelpService.searchPagingAllMemhelp(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex()));
		
		
		model.addAttribute("memberCount", memberCount);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("totalHelp", totalHelp);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("ordersAllList", ordersAllList);
		model.addAttribute("payDateList", payDateList);
		model.addAttribute("payMoneyList", payMoneyList);
		return "admin/adminHome";
	}

}
